package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.models.TodayShift;
import cz.kzrv.FacebookNotificationWorkBot.repository.TodayShiftRepository;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import cz.kzrv.FacebookNotificationWorkBot.util.TimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class TodayService {

    private final TodayShiftRepository todayShiftRepository;
    private final BotService botService;
    private final SendMessage sendMessage;

    @Autowired
    public TodayService(TodayShiftRepository todayShiftRepository, BotService botService, SendMessage sendMessage) {
        this.todayShiftRepository = todayShiftRepository;
        this.botService = botService;
        this.sendMessage = sendMessage;
    }

    public void addShift(Person person, TimeTable timeTable){
        TodayShift todayShift = new TodayShift();
        todayShift.setTimeTable(timeTable);
        todayShift.setOwner(person);
        todayShiftRepository.save(todayShift);
    }
    public void deleteAll(){
        todayShiftRepository.deleteAll();
    }
    public void sendNotificationForToday(){
        List<TodayShift> list = todayShiftRepository.findAll();
        for(TodayShift shift : list) {
            TimeTable timeTable = shift.getTimeTable();
            if (oneHour(timeTable, shift)) {
                String msg = "Dneska v " +
                        timeTable.getBegin() +
                        " do " + timeTable.getEnd()+" budeš mít směnu";
                sendMessage.sending(
                        shift.getOwner().getFacebookId(),
                        msg,
                        MessageType.CONFIRMED_EVENT_UPDATE);
            }
        }
    }
    private boolean oneHour(TimeTable timeTable,TodayShift todayShift){
        String[] split = timeTable.getBegin().split(":");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H");
        LocalTime localTime = LocalTime.now().plusHours(1);
        int hour = localTime.getHour();
        int start = Integer.parseInt(split[0]);
        if(start-hour==1 && !todayShift.getSent()) return true;
        return false;
    }
}
