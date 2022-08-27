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
        System.out.println(message.getOptin());
        if(notRegisteredPerson!=null && !notRegisteredPerson.getActivated()){
            notRegisteredPerson.setActivated(true);
            notRegisteredPerson.setFacebookId(message.getSender());
            notRegisteredPerson.setStatesOfBot(StatesOfBot.REQUEST);
            peopleService.save(notRegisteredPerson);
            fastSend("Byli jste úspěšně zaregistrováni",notRegisteredPerson.getFacebookId());
            messageDailyRequestService.execute(message.getSender());
        }
        if(registeredPerson!=null && message.getOptin()!=null && registeredPerson.getActivated()){
                Optin optin = message.getOptin();
                if(optin.getPayload().equals("PAYLOAD_DAILY_REQUEST")) {
                    if (optin.getMessageStatus() != null) {
                        if (optin.getMessageStatus().equals("RESUME_NOTIFICATIONS")) {
                            registeredPerson.setStatesOfBot(StatesOfBot.DEFAULT);
                            peopleService.save(registeredPerson);
                            fastSend("Znovu budete dostávat upozornění", registeredPerson.getFacebookId());
                        } else if (optin.getMessageStatus().equals("STOP_NOTIFICATIONS")) {
                            registeredPerson.setStatesOfBot(StatesOfBot.REQUEST);
                            peopleService.save(registeredPerson);
                            fastSend("Již nebudete dostávat oznámení", registeredPerson.getFacebookId());
                        }
                    } else if(registeredPerson.getStatesOfBot()==StatesOfBot.REQUEST){
                        registeredPerson.setStatesOfBot(StatesOfBot.DEFAULT);
                        peopleService.save(registeredPerson);
                        fastSend("Přihlášeno, díky. Po 6 měsících obdržíte upozornění na možnost obnovení předplatného. Také v případě výpovědi bude předplatné automaticky odstraněno", registeredPerson.getFacebookId());
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
            else fastSend("Neexistujicí příkaz, použijte prosím příkaz /help",registeredPerson.getFacebookId());
        }
    }
    private void fastSend(String text,String id){
        messageResponseService.sending(
                id,
                text,
                MessageType.RESPONSE
        );
    }
}
