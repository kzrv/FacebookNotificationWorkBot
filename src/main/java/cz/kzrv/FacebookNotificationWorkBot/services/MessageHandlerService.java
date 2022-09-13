package cz.kzrv.FacebookNotificationWorkBot.services;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import cz.kzrv.FacebookNotificationWorkBot.util.Generator;
import cz.kzrv.FacebookNotificationWorkBot.util.MessageType;
import cz.kzrv.FacebookNotificationWorkBot.util.StatesOfBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageHandlerService {
    private final PeopleService peopleService;
    private final MessageResponseService messageService;

    @Autowired
    public MessageHandlerService(PeopleService peopleService, MessageResponseService messageService) {
        this.peopleService = peopleService;
        this.messageService = messageService;
    }

    public void getCommand(Person person, String msg) {
        switch (msg){
            case "/pridat" :
                    person.setStatesOfBot(StatesOfBot.ADD_NEW_USER);
                    messageService.fastResponseWithReplies(
                            "Napiště prosím jméno človeka,ktereho chcete přídat(stejně jako bude zapsan v tabulce)", person);

                    peopleService.save(person);
                    break;
            case "/smazat" :
                person.setStatesOfBot(StatesOfBot.DELETE_USER);
                messageService.fastResponseWithReplies(
                        "Napiště prosím jméno človeka,ktereho chcete smazat(velkými písmeny)",person);
                peopleService.save(person);
                break;

            case "/lide" :
                List<Person> list = peopleService.getAllPeople();
                messageService.fastResponseWithReplies(listOfPeople(list),person);
                break;
            case "/help" :
                String msg1 = "`Příkazy:` \n"+
                        "*/pridat* - `přidat nového člověka`\n"+
                        "*/smazat* - `smazat osobu`\n"+
                        "*/lide* - `seznam všech pracovníků`\n"+
                        "*/odkaz* - `odkaz na stranku`";
                messageService.fastResponseWithReplies(msg1,person);
                break;
            case "/odkaz" :
                messageService.fastResponseWithReplies("https://www.facebook.com/workbotfordellipizza", person);
                break;
            case "/zpatky" :
                person.setStatesOfBot(StatesOfBot.DEFAULT);
                messageService.fastResponseWithReplies("Byl jste vrácen",person);
                peopleService.save(person);
                break;
            default :
                messageService.fastResponseWithReplies("Neexistující příkaz", person);
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
                    personNew.setCode(Generator.uniqueCode(msg));
                    personNew.setName(msg);
                    personNew.setStatesOfBot(StatesOfBot.DEFAULT);
                    save(person);
                    messageService.fastResponse(
                            "Uživatel byl úspěšně přidán\n" +
                                    "Activated kod:",person.getFacebookId()
                    );
                    peopleService.save(personNew);
                    Person person1 = peopleService.findByName(msg);
                    messageService.fastResponseWithReplies(
                            person1.getCode(),
                            person
                    );
                }
                else messageService.fastResponseWithReplies(
                        "Takový uživatel už existuje",
                        person
                );
                break;

            case DELETE_USER:
                Person personForDelete = peopleService.findByName(msg);
                if(personForDelete!=null){
                    save(person);
                    peopleService.delete(personForDelete);
                    messageService.fastResponseWithReplies(
                            "Uživatel byl úspěšně smazan",
                            person
                    );
                }else messageService.fastResponseWithReplies(
                        "Takový uživatel neexistuje",
                        person
                );
                break;
            case DEFAULT:
                messageService.fastResponseWithReplies("Neexistujicí příkaz, použijte prosím /help",person);
        }

    }

    private void save(Person person) {
        person.setStatesOfBot(StatesOfBot.DEFAULT);
        peopleService.save(person);
    }
    private String listOfPeople(List<Person> list){
        StringBuilder result = new StringBuilder();
        for(Person person : list){
            result.append("*")
                    .append(person.getName())
                    .append("*")
                    .append(":\nAktivován = ")
                    .append(clear(person.getActivated()))
                    .append("\nUpozornění = ")
                    .append(clear(person.getAvailNotif()))
                    .append("\nKod = `")
                    .append(person.getCode())
                    .append("`\n");
        }
        return result.toString();
    }
    private String clear(Boolean element){
        if(element) return "`ANO`";
        else return  "`NE`";
    }


}
