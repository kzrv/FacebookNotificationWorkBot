package cz.kzrv.FacebookNotificationWorkBot.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class Verify {
    @Value("${bot.token}")
    private String token;

    public boolean controll(String tokenCheck){
        if (token.equals(tokenCheck)) return true;
        return false;
    }
}
