package cz.kzrv.FacebookNotificationWorkBot.repository;

import cz.kzrv.FacebookNotificationWorkBot.models.TomorrowShift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TomorrowRepository extends JpaRepository<TomorrowShift,Integer> {
}
