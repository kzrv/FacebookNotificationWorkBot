package cz.kzrv.FacebookNotificationWorkBot.services;


import cz.kzrv.FacebookNotificationWorkBot.repository.BotRepository;
import cz.kzrv.FacebookNotificationWorkBot.models.Message;
import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@PropertySource("classpath:application.properties")
public class BotService {
    @Value("${bot.token}")
    private String token;
    private final BotRepository botRepository;
    private final SendMessage sendMessage;

    @Autowired
    public BotService(BotRepository botRepository, SendMessage sendMessage) {
        this.botRepository = botRepository;
        this.sendMessage = sendMessage;
    }

    public boolean controll(String tokenCheck){
        if (token.equals(tokenCheck)) return true;
        return false;
    }

    public void getMessageFromUser(Message message) {
        Person person = botRepository.findByCode(message.getMsg());
        System.out.println(message.getSender());
        if(person!=null && !person.getActivated()){
            person.setActivated(true);
            person.setFacebookId(message.getSender());
            botRepository.save(person);
            sendMessage.sending(person.getFacebookId(),
                    "You was successfully registered",
                    MessageType.RESPONSE
            );
        }
        else {
            sendMessage.sending(message.getSender(),
                    "Command is invalid or you're already registered",
                    MessageType.RESPONSE
            );
        }

    }
}
