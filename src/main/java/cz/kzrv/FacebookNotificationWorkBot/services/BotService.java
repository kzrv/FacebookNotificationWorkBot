package cz.kzrv.FacebookNotificationWorkBot.services;


import cz.kzrv.FacebookNotificationWorkBot.BotRepository;
import cz.kzrv.FacebookNotificationWorkBot.models.Message;
import cz.kzrv.FacebookNotificationWorkBot.models.Person;
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

    @Autowired
    public BotService(BotRepository botRepository) {
        this.botRepository = botRepository;
    }

    public boolean controll(String tokenCheck){
        if (token.equals(tokenCheck)) return true;
        return false;
    }

    public void getMessageFromUser(Message message) {
        Optional<Person> prsn = botRepository.findByCode(message.getMsg());
        if(prsn.isPresent() && !prsn.get().getActivated()){
            Person person = prsn.get();
            person.setActivated(true);
            person.setFacebookId(message.getSender());
            botRepository.save(person);
            System.out.println("OK");
        }
        System.out.println("WRONG");
    }
}
