package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.DTO.MessageEvent;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
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
public class MessageResponseService {

    private final RestTemplate restTemplate;

    @Value("${bot.token}")
    private String token;

    @Autowired
    public MessageResponseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void sending(String recipient,String msg, MessageType type){
        String URL = "https://graph.facebook.com/v14.0/me/messages?access_token="+token;
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

}