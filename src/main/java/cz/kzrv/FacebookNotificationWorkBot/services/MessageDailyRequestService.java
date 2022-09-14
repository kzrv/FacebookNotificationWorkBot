package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.DTO.*;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MessageDailyRequestService {
    private final RestTemplate restTemplate;

    @Value("${bot.token}")
    private String token;
    private final PeopleService peopleService;

    @Autowired
    public MessageDailyRequestService(RestTemplate restTemplate, PeopleService peopleService) {
        this.restTemplate = restTemplate;
        this.peopleService = peopleService;
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
    @Scheduled(cron = "${one_a_day_before}")
    public void checkTokens(){
        List<Person> list = peopleService.getAllPeople();
        for(Person person : list){
            if(person.getActivated() && person.getAvailNotif()){
                String URL = "https://graph.facebook.com/v14.0/notification_messages_"+person.getToken()+"?access_token="+token;
                TokenInfo tokenInfo = restTemplate.getForEntity(URL, TokenInfo.class).getBody();
                if(tokenInfo!=null && tokenInfo.getEndOfToken()!=null){
                    long time = Long.parseLong(tokenInfo.getEndOfToken());
                    long currentTime = System.currentTimeMillis();
                    if(time-currentTime<0){
                        person.setAvailNotif(false);
                        peopleService.save(person);
                    }
                }
            }
        }
    }
}
