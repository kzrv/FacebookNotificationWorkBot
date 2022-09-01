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
import java.util.stream.Collectors;

@Service
public class TomorrowService {
    private final TomorrowRepository tomorrowRepository;
    private final MessageResponseService messageResponseService;

    @Autowired
    public TomorrowService(TomorrowRepository tomorrowRepository, MessageResponseService messageResponseService) {
        this.tomorrowRepository = tomorrowRepository;
        this.messageResponseService = messageResponseService;
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
            if(shift!=null && shift.getOwner().getFacebookId()!=null){
                TimeTable timeTable = shift.getTimeTable();
                String msg = "Budeš zítra mít směnu od "+
                        timeTable.getBegin() +
                        " do " + timeTable.getEnd();
                List<TomorrowShift> list1 = list.stream().filter(s-> s.getOwner().getName().equals(shift.getOwner().getName()) &&
                                s!=shift).collect(Collectors.toList());
                if(!list1.isEmpty()){
                    for(TomorrowShift shiftCheck : list1){
                        timeTable = shiftCheck.getTimeTable();
                        msg += "a od "+
                                timeTable.getBegin() +
                                " do " + timeTable.getEnd();
                        list.remove(shiftCheck);
                    }
                }
                messageResponseService.sending(
                        shift.getOwner().getFacebookId(),
                        msg,
                        MessageType.CONFIRMED_EVENT_UPDATE);
            }
        }
    }
}
