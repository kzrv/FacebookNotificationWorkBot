package cz.kzrv.FacebookNotificationWorkBot.repository;

import cz.kzrv.FacebookNotificationWorkBot.models.TodayShift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayShiftRepository extends JpaRepository<TodayShift,Integer> {
}
