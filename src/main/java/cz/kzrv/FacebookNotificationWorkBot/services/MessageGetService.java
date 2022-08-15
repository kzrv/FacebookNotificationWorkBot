package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.dates.MessageHandler;
import cz.kzrv.FacebookNotificationWorkBot.dates.StatesOfBot;
import cz.kzrv.FacebookNotificationWorkBot.models.Message;
import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import org.springframework.stereotype.Service;

@Service
public class MessageGetService {
    private final PeopleService peopleService;
    private final MessageResponseService messageResponseService;
    private final MessageHandler messageHandler;
    public MessageGetService(PeopleService peopleService, MessageResponseService messageResponseService, MessageHandler messageHandler) {
        this.peopleService = peopleService;
        this.messageResponseService = messageResponseService;
        this.messageHandler = messageHandler;
    }

    public void gettingMessage(Message message){
        Person person = peopleService.getMessageFromUser(message.getMsg());
        Person admin = peopleService.findByFacebookID(message.getSender());
        if(person!=null && !person.getActivated()){
            person.setActivated(true);
            person.setFacebookId(message.getSender());
            person.setStatesOfBot(StatesOfBot.DEFAULT);
            peopleService.save(person);
            messageResponseService.sending(person.getFacebookId(),
                    "You was successfully registered",
                    MessageType.RESPONSE
            );
        }
        else if(admin!=null && admin.getAdmin() && admin.getActivated()){
            String msg  = message.getMsg();
            if(msg.startsWith("/")){
                messageHandler.getCommand(admin,msg);
            }
            else {
                messageHandler.handleResponse(admin,msg);
            }
        }
//        else {
//            messageService.sending(message.getSender(),
//                    "Command is invalid or you're already registered",
//                    MessageType.RESPONSE
//            );
//        }
    }
}
