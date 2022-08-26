package cz.kzrv.FacebookNotificationWorkBot.services;

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
        Person person = peopleService.getMessageFromUser(message.getMsg());
        Person admin = peopleService.findByFacebookID(message.getSender());
        if(person!=null && !person.getActivated()){
            person.setActivated(true);
            person.setFacebookId(message.getSender());
            person.setStatesOfBot(StatesOfBot.REQUEST);
            peopleService.save(person);
            messageResponseService.sending(person.getFacebookId(),
                    "Byli jste úspěšně zaregistrováni",
                    MessageType.RESPONSE
            );
            messageDailyRequestService.execute(message.getSender());
        }
        else if(admin!=null && admin.getAdmin() && admin.getActivated()){
            String msg  = message.getMsg();
            if(msg.startsWith("/")){
                messageHandler.getCommand(admin,msg);
            }
            else if(admin.getStatesOfBot()!=StatesOfBot.DEFAULT){
                messageHandler.handleResponse(admin,msg);
            }
            else messageResponseService.sending(
                    admin.getFacebookId(),
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
