package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.repository.PeopleRepository;
import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class PeopleService {
    @Value("${bot.token}")
    private String token;
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public boolean checkToken(String tokenCheck){
        if (token.equals(tokenCheck)) return true;
        return false;
    }
    public Person findByName(String name){
        return peopleRepository.findByName(name);
    }

    public Person getMessageFromUser(String message) {
        return peopleRepository.findByCode(message);
    }
    public void save(Person person){
        peopleRepository.save(person);
    }
    public List<Person> getAllPeople(){
        return peopleRepository.findAll();
    }
    public Person findByFacebookID(String id){
        return peopleRepository.findByFacebookId(id);
    }
}
