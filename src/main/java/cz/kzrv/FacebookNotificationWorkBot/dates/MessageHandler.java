package cz.kzrv.FacebookNotificationWorkBot.dates;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.services.MessageService;
import cz.kzrv.FacebookNotificationWorkBot.services.PeopleService;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageHandler {
    private final MessageService messageService;
    private final PeopleService peopleService;

    @Autowired
    public MessageHandler(MessageService messageService, PeopleService peopleService) {
        this.messageService = messageService;
        this.peopleService = peopleService;
    }

    public void getCommand(Person person, String msg) {
        switch (msg){
            case "/add" -> {
                    messageService.sending(
                            person.getFacebookId(),
                            "Napiště prosím jméno človeka,ktereho chcete smazat(Stejně jako v bude zapsan v tabulke)",
                            MessageType.RESPONSE);
                    person.setStatesOfBot(StatesOfBot.ADD_NEW_USER);
                    peopleService.save(person);
            }
            case "/delete" -> {
                messageService.sending(
                        person.getFacebookId(),
                        "Napiště prosím jméno človeka,ktereho chcete smazat(velkými písmeny)",
                        MessageType.RESPONSE);
                person.setStatesOfBot(StatesOfBot.DELETE_USER);
                peopleService.save(person);
            }
            case "/lide" -> {
                List<Person> list = peopleService.getAllPeople();
                list.forEach(s->messageService.sending(person.getFacebookId(),
                        list.toString(),
                        MessageType.RESPONSE));
            }
            case "/help" -> {
                String msg1 = "Příkazy: \n"+
                        "/add - přidat nového člověka\n"+
                        "/delete - smazat osobu\n"+
                        "/lide - seznam všech pracovníků";
                messageService.sending(person.getFacebookId(),
                        msg1,
                        MessageType.RESPONSE);
            }
            default -> {
                messageService.sending(person.getFacebookId(),
                        "Neexistující příkaz",
                        MessageType.RESPONSE);
            }
        }
    }
    public void handleResponse(){

    }


}
