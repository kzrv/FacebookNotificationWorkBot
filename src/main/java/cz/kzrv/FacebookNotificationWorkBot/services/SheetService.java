package cz.kzrv.FacebookNotificationWorkBot.services;

import com.google.api.services.sheets.v4.Sheets;

import com.google.api.services.sheets.v4.model.ValueRange;
import cz.kzrv.FacebookNotificationWorkBot.config.GoogleAuthorizationConfig;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.util.Month;
import cz.kzrv.FacebookNotificationWorkBot.util.TimeTable;
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


    private final GoogleAuthorizationConfig googleAuthorizationConfig;
    private final PeopleService peopleService;
    private final TomorrowService tomorrowService;

    @Autowired
    public SheetService(GoogleAuthorizationConfig googleAuthorizationConfig, PeopleService peopleService, TomorrowService tomorrowService) {
        this.googleAuthorizationConfig = googleAuthorizationConfig;
        this.peopleService = peopleService;
        this.tomorrowService = tomorrowService;
    }
    @Scheduled(cron = "${one_a_day}")
    public void getTomorrowShifts() throws IOException, GeneralSecurityException {
        Sheets sheetsService = googleAuthorizationConfig.getSheetService();
        tomorrowService.deleteAll();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadSheetId, getRange())
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List<Object> row : values) {
                for(int i = 0 ; i<row.size();i++){
                    String name =row.get(i).toString();
                    if(name!=null){
                        name = name.trim();
                        Person person = peopleService.findByName(name);
                        if(person!=null){
                            tomorrowService.addShift(person,TimeTable.getById(i));
                        }
                    }
                }
            }
        }
    }

    private String getRange(){
        final long plus = 1;
        // this variable returns us the number of lines before the beginning of the list with shifts
        final int rowOfDate = 4;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M");
        LocalDate localDate = LocalDate.now(ZoneId.of("UTC+2"));
        String[] split = formatter.format(localDate.plusDays(plus)).split("/");
        int day = Integer.parseInt(split[0])+ rowOfDate;
        int month = Integer.parseInt(split[1]);
        String currenMonth = Month.getMonthName(month);
//        String currenMonth = "work";
        return currenMonth+"!B"+day+":O"+day;
    }
}

