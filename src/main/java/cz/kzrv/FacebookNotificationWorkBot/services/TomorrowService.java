package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.models.TomorrowShift;
import cz.kzrv.FacebookNotificationWorkBot.repository.TomorrowRepository;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import cz.kzrv.FacebookNotificationWorkBot.util.TimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TomorrowService {
    private final TomorrowRepository tomorrowRepository;
    private final MessageService messageService;

    @Autowired
    public TomorrowService(TomorrowRepository tomorrowRepository, MessageService messageService) {
        this.tomorrowRepository = tomorrowRepository;
        this.messageService = messageService;
    }
    public void addShift(Person person, TimeTable timeTable){
        TomorrowShift tomorrowShift = new TomorrowShift();
        tomorrowShift.setTimeTable(timeTable);
        tomorrowShift.setOwner(person);
        tomorrowRepository.save(tomorrowShift);
    }
    public void deleteAll(){
        tomorrowRepository.deleteAll();
    }
    @Scheduled(cron = "${one_a_day_after}")
    public void sendNotificationForTomorrow(){
        List<TomorrowShift> list = tomorrowRepository.findAll();
        for(TomorrowShift shift : list){
            if(shift.getOwner().getFacebookId()!=null){
                TimeTable timeTable = shift.getTimeTable();
                String msg = "Budeš zítra mít směnu od "+
                        timeTable.getBegin() +
                        " do " + timeTable.getEnd();
                messageService.sending(
                        shift.getOwner().getFacebookId(),
                        msg,
                        MessageType.CONFIRMED_EVENT_UPDATE);
            }
        }
    }
}
