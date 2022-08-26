package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.DTO.Optin;
import cz.kzrv.FacebookNotificationWorkBot.dates.MessageHandler;
import cz.kzrv.FacebookNotificationWorkBot.dates.StatesOfBot;
import cz.kzrv.FacebookNotificationWorkBot.models.Message;
import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageGetService {
    private final PeopleService peopleService;
    private final MessageResponseService messageResponseService;
    private final MessageHandler messageHandler;
    private final MessageDailyRequestService messageDailyRequestService;

    @Autowired
    public MessageGetService(PeopleService peopleService, MessageResponseService messageResponseService, MessageHandler messageHandler, MessageDailyRequestService messageDailyRequestService) {
        this.peopleService = peopleService;
        this.messageResponseService = messageResponseService;
        this.messageHandler = messageHandler;
        this.messageDailyRequestService = messageDailyRequestService;
    }

    public void gettingMessage(Message message){
        Person notRegisteredPerson = peopleService.getMessageFromUser(message.getMsg());
        Person registeredPerson = peopleService.findByFacebookID(message.getSender());
        if(notRegisteredPerson!=null && !notRegisteredPerson.getActivated()){
            notRegisteredPerson.setActivated(true);
            notRegisteredPerson.setFacebookId(message.getSender());
            notRegisteredPerson.setStatesOfBot(StatesOfBot.REQUEST);
            peopleService.save(notRegisteredPerson);
            messageResponseService.sending(notRegisteredPerson.getFacebookId(),
                    "Byli jste úspěšně zaregistrováni",
                    MessageType.RESPONSE
            );
            messageDailyRequestService.execute(message.getSender());
        }
        if(registeredPerson!=null && registeredPerson.getStatesOfBot()==StatesOfBot.REQUEST && registeredPerson.getActivated()){
            if(message.getOptin()!=null){
                Optin optin = message.getOptin();
                if(optin.getPayload().equals("PAYLOAD_DAILY_REQUEST")){
                    System.out.println(optin.getTokenStatus());
                    registeredPerson.setStatesOfBot(StatesOfBot.DEFAULT);
                }
            }
        }
        if(registeredPerson!=null && registeredPerson.getAdmin() && registeredPerson.getActivated()){
            String msg  = message.getMsg();
            if(msg.startsWith("/")){
                messageHandler.getCommand(registeredPerson,msg);
            }
            else if(registeredPerson.getStatesOfBot()!=StatesOfBot.DEFAULT){
                messageHandler.handleResponse(registeredPerson,msg);
            }
            else messageResponseService.sending(
                    registeredPerson.getFacebookId(),
                        "Neexistujicí příkaz, použijte prosím příkaz /help",
                        MessageType.RESPONSE
                );
        }
//        else {
//            messageService.sending(message.getSender(),
//                    "Command is invalid or you're already registered",
//                    MessageType.RESPONSE
//            );
//        }
    }
}
