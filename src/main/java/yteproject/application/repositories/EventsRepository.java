package yteproject.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yteproject.application.entities.Events;

import javax.transaction.Transactional;

@Repository
public interface EventsRepository extends JpaRepository<Events,Long> {
    void deleteByEventName(String eventName);
    Events findByEventName(String eventName);
    @Modifying
    @Transactional
    @Query("update Events events set events.eventName=:newEventName where events.eventName=:eventName")
    void updateJustNameByName(@Param("eventName")String eventName,@Param("newEventName")String newEventName);
}
