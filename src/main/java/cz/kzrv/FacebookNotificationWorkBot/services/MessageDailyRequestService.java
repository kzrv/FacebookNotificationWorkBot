package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.DTO.Attachment;
import cz.kzrv.FacebookNotificationWorkBot.DTO.MessageDailyRequest;
import cz.kzrv.FacebookNotificationWorkBot.DTO.MessageEvent;
import cz.kzrv.FacebookNotificationWorkBot.DTO.Payload;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageResponse;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageDailyRequestService {
    private final RestTemplate restTemplate;

    @Value("${bot.token}")
    private String token;

    @Autowired
    public MessageDailyRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void execute(String id){
        final String URL = "https://graph.facebook.com/v14.0/me/messages?access_token=" + token;
        MessageDailyRequest msg = new MessageDailyRequest();
        msg.setRecipient(new Recipient(id));
        Payload payload = new Payload();
        payload.setTemplateType("notification_messages");
        payload.setTitle("Dejte prosím souhlas se zasíláním pravidelných upozornění");
        payload.setImageUrl("https://cdn.pixabay.com/photo/2022/08/18/09/20/houses-7394390_1280.jpg");
        payload.setPayload("PAYLOAD_DAILY_REQUEST");
        payload.setNotifMsgFrequency("DAILY");
        payload.setNotifMsgReoptin("ENABLED");
        payload.setNotifMsgTimezone("Poland");
        MessageEvent message = new MessageEvent();
        message.setAttachment(new Attachment("template",payload));
        msg.setMessage(message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MessageDailyRequest> response = new HttpEntity<>(msg,headers);
        try{
            restTemplate.postForEntity(URL,response,String.class);

        }catch (RestClientException e){
            System.out.println("!!!!!!!!EXCEPTION WHILE SENDING REQUEST!!!!!!!!!!");
            System.out.println(e.getLocalizedMessage());
        }
    }
}
