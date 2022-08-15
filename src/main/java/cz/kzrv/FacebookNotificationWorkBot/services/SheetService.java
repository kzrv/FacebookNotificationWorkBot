package cz.kzrv.FacebookNotificationWorkBot.services;

import com.google.api.services.sheets.v4.Sheets;

import com.google.api.services.sheets.v4.model.ValueRange;
import cz.kzrv.FacebookNotificationWorkBot.config.GoogleAuthorizationConfig;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.util.Month;
import cz.kzrv.FacebookNotificationWorkBot.util.TimeTable;
import cz.kzrv.FacebookNotificationWorkBot.util.TimeTable2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SheetService {

    @Value("${spreadsheet.id}")
    private String spreadSheetId;
    private final int rowOfDate = 4; // this variable returns us the number of lines before the beginning of the list with shifts


    private final GoogleAuthorizationConfig googleAuthorizationConfig;
    private final PeopleService peopleService;
    private final TodayService todayService;
    private final TomorrowService tomorrowShift;

    @Autowired
    public SheetService(GoogleAuthorizationConfig googleAuthorizationConfig, PeopleService peopleService, TodayService todayService, TomorrowService tomorrowShift) {
        this.googleAuthorizationConfig = googleAuthorizationConfig;
        this.peopleService = peopleService;
        this.todayService = todayService;
        this.tomorrowShift = tomorrowShift;
    }
    @Scheduled(cron = "${one_a_day}")
    public void getSpreadsheetValues() throws IOException, GeneralSecurityException {
        todayService.deleteAll();
        Sheets sheetsService = googleAuthorizationConfig.getSheetService();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadSheetId, getRange(0))
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List<Object> row : values) {
               for(int i = 0 ; i<row.size();i++){
                  if(row.get(i)!=null){
                      Person person = peopleService.findByName(row.get(i).toString());
                      if(person!=null && person.getActivated()){
                          todayService.addShift(person, TimeTable.getById(i));
                      }
                  }
               }
            }
        }
        getSpreadsheetValues1(sheetsService);
    }

    public void getSpreadsheetValues1(Sheets sheetsService) throws IOException {
        tomorrowShift.deleteAll();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadSheetId, getRange(1))
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List<Object> row : values) {
                for(int i = 0 ; i<row.size();i++){
                    String name =row.get(i).toString();
                    if(name!=null){
                        if (name.contains("/")) {
                            String[] nameArray = name.split("/");
                            if (!nameArray[0].equals("")) {
                                callByName(nameArray[0], i,true);
                            } else if (!nameArray[1].equals("")) {
                                callByName(nameArray[1],i,false);
                            }
                        }
                    }
                }
            }
        }
    }


    private String getRange(long plus){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M");
        LocalDate localDate = LocalDate.now(ZoneId.of("UTC+2"));
        String[] split = formatter.format(localDate.plusDays(plus)).split("/");
        int day = Integer.parseInt(split[0])+rowOfDate;
        int month = Integer.parseInt(split[1]);
        String currenMonth = Month.getMonthName(month);
        return currenMonth+"!B"+day+":I"+day;
    }
    private void callByName(String name,int i,boolean shift){
        Person person = peopleService.findByName(name.trim());
        if(person!=null && person.getActivated()){
            if(shift)tomorrowShift.addShift(person, TimeTable.getById(i));
            else tomorrowShift.addShift(person, TimeTable2.getById(i));
        }

    }
}

