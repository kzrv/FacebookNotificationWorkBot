package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.models.TodayShift;
import cz.kzrv.FacebookNotificationWorkBot.repository.TodayShiftRepository;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import cz.kzrv.FacebookNotificationWorkBot.util.TimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class TodayService {

    private final TodayShiftRepository todayShiftRepository;
    private final PeopleService peopleService;
    private final MessageService messageService;

    @Autowired
    public TodayService(TodayShiftRepository todayShiftRepository, PeopleService peopleService, MessageService messageService) {
        this.todayShiftRepository = todayShiftRepository;
        this.peopleService = peopleService;
        this.messageService = messageService;
    }

    public void addShift(Person person, TimeTable timeTable) {
        TodayShift todayShift = new TodayShift();
        todayShift.setTimeTable(timeTable);
        todayShift.setOwner(person);
        todayShift.setSent(false);
        todayShiftRepository.save(todayShift);
    }

    public void deleteAll() {
        todayShiftRepository.deleteAll();
    }
    @Scheduled(cron ="@hourly")
    public void sendNotificationForToday() {
        List<TodayShift> list = todayShiftRepository.findAll();
        for (TodayShift shift : list) {
            TimeTable timeTable = shift.getTimeTable();
            if (oneHour(timeTable, shift)) {
                shift.setSent(true);
                todayShiftRepository.save(shift);
                String msg = "Dneska od " +
                        timeTable.getBegin() +
                        " do " + timeTable.getEnd() + " budeš mít směnu";
                messageService.sending(
                        shift.getOwner().getFacebookId(),
                        msg,
                        MessageType.CONFIRMED_EVENT_UPDATE);
            }
        }
    }

    private boolean oneHour(TimeTable timeTable, TodayShift todayShift) {
        String[] split = timeTable.getBegin().split(":");
        String str = String.valueOf(LocalTime.now().getHour())+String.valueOf(LocalTime.now().getMinute());
        int hour = Integer.parseInt(str);
        int start = Integer.parseInt(split[0]+split[1]);
        int fin = start - hour;
        return fin < 100 && fin>0 && !todayShift.getSent();
    }
}
