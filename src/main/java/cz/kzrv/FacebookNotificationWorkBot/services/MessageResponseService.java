package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.DTO.MessageEvent;
import cz.kzrv.FacebookNotificationWorkBot.DTO.QuickReplies;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.util.FollowingMessageResponse;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageResponse;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import cz.kzrv.FacebookNotificationWorkBot.util.StatesOfBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class MessageResponseService {

    private final RestTemplate restTemplate;

    @Value("${bot.token}")
    private String token;

    @Autowired
    public MessageResponseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void sending(String recipient,String msg, MessageType type,StatesOfBot states){
        String URL = "https://graph.facebook.com/v14.0/me/messages?access_token="+token;
        MessageResponse messageResponse = new MessageResponse();
        MessageEvent messageEvent = new MessageEvent();
        if(states!=null){
            if(states==StatesOfBot.DEFAULT){
                messageEvent.setQuickReplies(defaultList());
            }
            else {
                List<QuickReplies> list =new ArrayList<>();
                list.add(quickReplie("/zpatky"));
                messageEvent.setQuickReplies(list);
            }
        }
        messageEvent.setText(msg);
        messageResponse.setRecipient(new Recipient(recipient));
        messageResponse.setMessage(messageEvent);
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
    public void sendNotification(Person person, String message){
        String URL = "https://graph.facebook.com/v14.0/me/notification_messages_dev_support?access_token=" + token;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        FollowingMessageResponse following = new FollowingMessageResponse();
        following.setDevAction("ENABLE_FOLLOWUP_MESSAGE");
        Recipient recipient = new Recipient();
        recipient.setNotMessageToken(person.getToken());
        following.setRecipient(recipient);
        HttpEntity<FollowingMessageResponse> entity = new HttpEntity<>(following,headers);
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(URL,entity,String.class);
            if(result.toString().contains("true")){
                sendNotificationMessage(person.getFacebookId(),message);
            }
            else System.out.println("ERROR SEND NOTIFICATION");
        }
        catch (RestClientException e){
            sendNotificationMessage(person.getFacebookId(),message);
        }

    }
    public void fastResponse(String msg,String recipient){
        sending(recipient,msg,MessageType.RESPONSE,null);
    }
    public void fastResponseWithReplies(String msg, Person person){
        sending(person.getFacebookId(),msg,MessageType.RESPONSE,person.getStatesOfBot());
    }
    private void sendNotificationMessage(String recipient,String message){
        String URL = "https://graph.facebook.com/v14.0/me/messages?access_token="+token;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setRecipient(new Recipient(recipient));
        messageResponse.setType(MessageType.MESSAGE_TAG.name());
        messageResponse.setTag(MessageType.CONFIRMED_EVENT_UPDATE.name());
        messageResponse.setMessage(new MessageEvent(message));
        HttpEntity<MessageResponse> httpEntity = new HttpEntity<>(messageResponse,headers);
        try{
            restTemplate.postForEntity(URL,httpEntity,String.class);

        }catch (RestClientException e){
            System.out.println("!!!!!!!!EXCEPTION WHILE SENDING RESPONSE!!!!!!!!!!");
            System.out.println(e.getLocalizedMessage());
        }
    }
    private QuickReplies quickReplie(String title){
        QuickReplies quickReplies = new QuickReplies();
        quickReplies.setContentType("text");
        quickReplies.setPayload(title);
        quickReplies.setTitle(title);
        return quickReplies;
    }
    private List<QuickReplies> defaultList(){
        List<QuickReplies> list = new ArrayList<>();
        list.add(quickReplie("/pridat"));
        list.add(quickReplie("/smazat"));
        list.add(quickReplie("/lide"));
        list.add(quickReplie("/odkaz"));
        list.add(quickReplie("/help"));
        return list;
    }


}
