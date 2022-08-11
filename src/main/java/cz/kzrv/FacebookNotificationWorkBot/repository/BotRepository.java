package cz.kzrv.FacebookNotificationWorkBot.repository;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BotRepository extends JpaRepository<Person,Integer> {
    Person findByCode(String code);
    Person findByName(String name);
}
