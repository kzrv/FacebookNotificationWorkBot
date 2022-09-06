package cz.kzrv.FacebookNotificationWorkBot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class CallBackController {

    @GetMapping("private_policy")
    public String privatePolicy(){
        return "info/privatePolicy";
    }
    @GetMapping()
    public String info(){
        return "info/info";
    }
}
