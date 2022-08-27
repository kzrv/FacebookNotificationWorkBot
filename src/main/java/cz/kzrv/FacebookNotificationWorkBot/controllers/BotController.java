package cz.kzrv.FacebookNotificationWorkBot.controllers;

import cz.kzrv.FacebookNotificationWorkBot.DTO.Event;
import cz.kzrv.FacebookNotificationWorkBot.services.MessageGetService;
import cz.kzrv.FacebookNotificationWorkBot.models.Message;
import cz.kzrv.FacebookNotificationWorkBot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BotController {
    private final PeopleService peopleService;
    private final MessageGetService messageGetService;
    @Autowired
    public BotController(PeopleService peopleService, MessageGetService messageGetService) {
        this.peopleService = peopleService;
        this.messageGetService = messageGetService;
    }

    @GetMapping("/webhook")
    public ResponseEntity<String> start(@RequestParam("hub.mode")String subscribe,
                        @RequestParam("hub.verify_token")String token,
                        @RequestParam("hub.challenge")String response){
        if(peopleService.checkToken(token) && subscribe.equals("subscribe")){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("BAD TOKEN");

    }
    @PostMapping("/webhook")
    public ResponseEntity<HttpStatus> webhook(@RequestBody Event event){
            messageGetService.gettingMessage(transfer(event));
            return ResponseEntity.ok(HttpStatus.OK);

    }

    private Message transfer(Event event){
        Message message = new Message();
        if(event.getEntry().get(0).getMessaging().get(0).getMessage()!=null)
            message.setMsg(event.getEntry().get(0).getMessaging().get(0).getMessage().getText());
        else message.setMsg("0");
        message.setSender(event.getEntry().get(0).getMessaging().get(0).getSender().getId());
        message.setOptin(event.getEntry().get(0).getMessaging().get(0).getOptin());
        return message;
    }

}
