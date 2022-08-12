package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.DTO.MessageEvent;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import cz.kzrv.FacebookNotificationWorkBot.dates.MessageHandler;
import cz.kzrv.FacebookNotificationWorkBot.dates.StatesOfBot;
import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.models.Message;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageResponse;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:application.properties")
public class MessageService {

    private final PeopleService peopleService;
    private final MessageHandler messageHandler;

    @Value("${bot.token}")
    private String token;

    @Autowired
    public MessageService(PeopleService peopleService, MessageHandler messageHandler) {
        this.peopleService = peopleService;
        this.messageHandler = messageHandler;
    }

    public void sending(String recipient,String msg, MessageType type){
        String URL = "https://graph.facebook.com/v14.0/me/messages?access_token="+token;
        RestTemplate restTemplate = new RestTemplate();
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setRecipient(new Recipient(recipient));
        messageResponse.setMessage(new MessageEvent(msg));
        messageResponse.setType(type.name());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MessageResponse> response = new HttpEntity<>(messageResponse,headers);
        try{
            restTemplate.postForEntity(URL,response,String.class);

        }catch (RestClientException e){
            System.out.println("!!!!!!!!EXCEPTION WHILE SENDING RESPONSE!!!!!!!!!!");
            System.out.println(e.getLocalizedMessage());
        }
    }
    public void gettingMessage(Message message){
        Person person = peopleService.getMessageFromUser(message.getMsg());
        Person admin = peopleService.findByFacebookID(message.getSender());
        if(person!=null && !person.getActivated()){
            person.setActivated(true);
            person.setFacebookId(message.getSender());
            person.setStatesOfBot(StatesOfBot.DEFAULT);
            peopleService.save(person);
            sending(person.getFacebookId(),
                    "You was successfully registered",
                    MessageType.RESPONSE
            );
        }
        else if(admin.getAdmin() && admin.getActivated()){
            String msg  = message.getMsg();
            if(msg.startsWith("/")){
                messageHandler.getCommand(person,msg);
            }
            else {

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
