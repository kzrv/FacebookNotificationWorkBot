package cz.kzrv.FacebookNotificationWorkBot;

import cz.kzrv.FacebookNotificationWorkBot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BotRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByCode(String code);
}
