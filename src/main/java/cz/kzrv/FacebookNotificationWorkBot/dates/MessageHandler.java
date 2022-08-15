package cz.kzrv.FacebookNotificationWorkBot.dates;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.services.MessageResponseService;
import cz.kzrv.FacebookNotificationWorkBot.services.PeopleService;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageHandler{
    private final PeopleService peopleService;
    private final MessageResponseService messageService;

    @Autowired
    public MessageHandler(PeopleService peopleService, MessageResponseService messageService) {
        this.peopleService = peopleService;
        this.messageService = messageService;
    }

    public void getCommand(Person person, String msg) {
        switch (msg){
            case "/add" :
                    messageService.sending(
                            person.getFacebookId(),
                            "Napiště prosím jméno človeka,ktereho chcete smazat(Stejně jako v bude zapsan v tabulke)",
                            MessageType.RESPONSE);
                    person.setStatesOfBot(StatesOfBot.ADD_NEW_USER);
                    peopleService.save(person);
                    break;
            case "/delete" :
                messageService.sending(
                        person.getFacebookId(),
                        "Napiště prosím jméno človeka,ktereho chcete smazat(velkými písmeny)",
                        MessageType.RESPONSE);
                person.setStatesOfBot(StatesOfBot.DELETE_USER);
                peopleService.save(person);
                break;

            case "/lide" :
                List<Person> list = peopleService.getAllPeople();
                messageService.sending(person.getFacebookId(),
                        list.toString(),
                        MessageType.RESPONSE);
                break;
            case "/help" :
                String msg1 = "Příkazy: \n"+
                        "/add - přidat nového člověka\n"+
                        "/delete - smazat osobu\n"+
                        "/lide - seznam všech pracovníků";
                messageService.sending(person.getFacebookId(),
                        msg1,
                        MessageType.RESPONSE);
                break;
            default :
                messageService.sending(person.getFacebookId(),
                        "Neexistující příkaz",
                        MessageType.RESPONSE);
                break;
        }
    }
    public void handleResponse(Person person,String msg){
        StatesOfBot state =  person.getStatesOfBot();
        switch (state){
            case ADD_NEW_USER:
                if(peopleService.findByName(msg)==null){
                    Person personNew = new Person();
                    personNew.setActivated(false);
                    personNew.setAdmin(false);
                    personNew.setCode("sds");
                    personNew.setName(msg);
                    messageService.sending(
                            person.getFacebookId(),
                            "Uživatel byl úspěšně přidán",
                            MessageType.RESPONSE
                    );
                    save(person);
                }
                else messageService.sending(
                        person.getFacebookId(),
                        "Takový uživatel už existuje",
                        MessageType.RESPONSE
                );
                break;

            case DELETE_USER:
                Person personForDelete = peopleService.findByName(msg);
                if(personForDelete!=null){
                    peopleService.delete(personForDelete);
                    messageService.sending(
                            person.getFacebookId(),
                            "Uživatel byl úspěšně odebran",
                            MessageType.RESPONSE
                    );
                    save(person);
                }else messageService.sending(
                        person.getFacebookId(),
                        "Takový uživatel neexistuje",
                        MessageType.RESPONSE
                );
                break;
        }

    }

    private void save(Person person) {
        person.setStatesOfBot(StatesOfBot.DEFAULT);
        peopleService.save(person);
    }


}
