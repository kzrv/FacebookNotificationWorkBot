package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.models.TodayShift;
import cz.kzrv.FacebookNotificationWorkBot.repository.TodayShiftRepository;
import cz.kzrv.FacebookNotificationWorkBot.util.TimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodayService {

    private final TodayShiftRepository todayShiftRepository;
    private final BotService botService;

    @Autowired
    public TodayService(TodayShiftRepository todayShiftRepository, BotService botService) {
        this.todayShiftRepository = todayShiftRepository;
        this.botService = botService;
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
}
