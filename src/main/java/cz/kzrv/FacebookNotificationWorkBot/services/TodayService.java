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
    private final BotService botService;
    private final SendMessage sendMessage;

    @Autowired
    public TodayService(TodayShiftRepository todayShiftRepository, BotService botService, SendMessage sendMessage) {
        this.todayShiftRepository = todayShiftRepository;
        this.botService = botService;
        this.sendMessage = sendMessage;
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
                sendMessage.sending(
                        shift.getOwner().getFacebookId(),
                        msg,
                        MessageType.CONFIRMED_EVENT_UPDATE);
            }
        }
    }

    private boolean oneHour(TimeTable timeTable, TodayShift todayShift) {
        String[] split = timeTable.getBegin().split(":");
        LocalTime localTime = LocalTime.now();
        int hour = localTime.getHour();
        int start = Integer.parseInt(split[0]);
        return start - hour == 1 && !todayShift.getSent();
    }
}
