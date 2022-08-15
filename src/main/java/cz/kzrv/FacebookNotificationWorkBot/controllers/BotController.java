package cz.kzrv.FacebookNotificationWorkBot.controllers;

import cz.kzrv.FacebookNotificationWorkBot.DTO.Event;
import cz.kzrv.FacebookNotificationWorkBot.services.MessageGetService;
import cz.kzrv.FacebookNotificationWorkBot.models.Message;
import cz.kzrv.FacebookNotificationWorkBot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<HttpStatus> webhook(@RequestBody @Valid Event event, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
                messageGetService.gettingMessage(transfer(event));
                return ResponseEntity.ok(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private Message transfer(Event event){
        Message message = new Message();
        message.setSender(event.getEntry().get(0).getMessaging().get(0).getSender().getId());
        message.setMsg(event.getEntry().get(0).getMessaging().get(0).getMessage().getText());
        return message;
    }

}
