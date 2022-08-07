package cz.kzrv.FacebookNotificationWorkBot.controllers;

import cz.kzrv.FacebookNotificationWorkBot.models.Event;
import cz.kzrv.FacebookNotificationWorkBot.services.Verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BotController {
    private final Verify verify;

    @Autowired
    public BotController(Verify verify) {
        this.verify = verify;
    }

    @GetMapping("/webhooks")
    public ResponseEntity<String> start(@RequestParam("hub.mode")String subscribe,
                        @RequestParam("hub.verify_token")String token,
                        @RequestParam("hub.challenge")String response){
        if(verify.controll(token) && subscribe.equals("subscribe")){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("BAD TOKEN");

    }
    @PostMapping("/webhook")
    public ResponseEntity<HttpStatus> webhook(@RequestBody Event event){
        System.out.println(event.getEntry().get(0).getMessaging().get(0).getMessage().getText());
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
