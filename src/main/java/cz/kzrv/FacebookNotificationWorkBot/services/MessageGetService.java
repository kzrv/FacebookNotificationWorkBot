package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.DTO.Optin;
import cz.kzrv.FacebookNotificationWorkBot.util.StatesOfBot;
import cz.kzrv.FacebookNotificationWorkBot.models.Message;
import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageGetService {
    private final PeopleService peopleService;
    private final MessageResponseService messageResponseService;
    private final MessageHandlerService messageHandlerService;
    private final MessageDailyRequestService messageDailyRequestService;

    @Autowired
    public MessageGetService(PeopleService peopleService, MessageResponseService messageResponseService, MessageHandlerService messageHandlerService, MessageDailyRequestService messageDailyRequestService) {
        this.peopleService = peopleService;
        this.messageResponseService = messageResponseService;
        this.messageHandlerService = messageHandlerService;
        this.messageDailyRequestService = messageDailyRequestService;
    }

    public void gettingMessage(Message message){
        Person unknownPerson = peopleService.findByFacebookID(message.getSender());
        if(unknownPerson==null){//If person is not registered
            Person notRegistered = peopleService.findByMessage(message.getMsg());
            if(notRegistered!=null){//Checking if he sent activation code
                notRegistered.setFacebookId(message.getSender());
                notRegistered.setActivated(true);
                notRegistered.setStatesOfBot(StatesOfBot.DEFAULT);
                notRegistered.setAdmin(false);
                notRegistered.setAvailNotif(false);
                peopleService.save(notRegistered);
                messageResponseService.fastResponse("Byli jste úspěšně zaregistrováni",message.getSender());
                messageDailyRequestService.execute(message.getSender());
            }
        }
        else {//if this person was registered
            if(!unknownPerson.getAvailNotif()){
                Optin optin = message.getOptin();
                if(optin!=null && optin.getPayload().equals("PAYLOAD_DAILY_REQUEST")){//checking payloads
                    if (optin.getMessageStatus()!=null) {
                        if (optin.getMessageStatus().equals("RESUME_NOTIFICATIONS")) {
                            unknownPerson.setAvailNotif(true);
                            unknownPerson.setToken(optin.getNotifToken());
                            peopleService.save(unknownPerson);
                            messageResponseService.fastResponse("Znovu budete dostávat upozornění", unknownPerson.getFacebookId());
                        } else if (optin.getMessageStatus().equals("STOP_NOTIFICATIONS")) {
                            unknownPerson.setAvailNotif(false);
                            unknownPerson.setToken(optin.getNotifToken());
                            peopleService.save(unknownPerson);
                            messageResponseService.fastResponse("Již nebudete dostávat oznámení", unknownPerson.getFacebookId());
                        }
                    }
                    else {
                        unknownPerson.setAvailNotif(true);
                        unknownPerson.setToken(optin.getNotifToken());
                        peopleService.save(unknownPerson);
                        messageResponseService.fastResponse("Přihlášeno, díky. Po 6 měsících obdržíte upozornění na " +
                                "možnost obnovení předplatného. Také v případě výpovědi bude předplatné automaticky odstraněno", unknownPerson.getFacebookId());
                    }
                }
            }
            else {
                if(unknownPerson.getAdmin()){ // check admin commands
                    String msg = message.getMsg();
                    if(msg.startsWith("/")) messageHandlerService.getCommand(unknownPerson,msg);
                    else messageHandlerService.handleResponse(unknownPerson,msg);
                }
            }
        }
    }

}
