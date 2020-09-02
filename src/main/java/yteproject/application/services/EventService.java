package yteproject.application.services;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import yteproject.application.dtos.EventsDto;
import yteproject.application.dtos.EventsWithApplicantsCount;
import yteproject.application.dtos.EventsWithApplicationDates;
import yteproject.application.entities.Events;
import yteproject.application.mapper.EventsMapper;
import yteproject.application.messages.MessageResponse;
import yteproject.application.messages.MessageType;
import yteproject.application.repositories.EventsRepository;
import yteproject.application.repositories.PeopleRepository;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventsRepository eventsRepository;
    private final PeopleRepository peopleRepository;
    private final EventsMapper eventsMapper;
    public MessageResponse addEvent(Events events) {
        var exists = eventsRepository.findByEventName(events.getEventName());
        if(exists == null )  eventsRepository.save(events);
        else return new MessageResponse("Event name already exists!", MessageType.ERROR);
        return new MessageResponse("Successfully added new event",MessageType.SUCCESS);
    }

    public MessageResponse updateEvent(String eventName,Events events) {
        try {
            Events eventToUpdate = eventsRepository.findByEventName(eventName);
            eventToUpdate.setEventStartTime(events.getEventStartTime());
            eventToUpdate.setEventEndTime(events.getEventEndTime());
            eventToUpdate.setQuota(events.getQuota());
            eventToUpdate.setLatitude(events.getLatitude());
            eventToUpdate.setLongitude(events.getLongitude());
//        eventToUpdate.setEventName(events.getEventName());
            eventsRepository.updateJustNameByName(eventName, events.getEventName());
            eventsRepository.save(eventToUpdate);
        }   catch (Exception e) {
            var violations=((ConstraintViolationException)e).getConstraintViolations().stream().map(violation -> violation.getMessage());
            var violationMessage = violations.collect(Collectors.toList());
            return new MessageResponse(violationMessage.get(0), MessageType.ERROR);
        }
        return new MessageResponse("Successfully updated event", MessageType.SUCCESS);

    }

    public List<EventsDto> getEvents() {
        var events = new ArrayList<EventsDto>();
        eventsRepository.findAll().forEach(event -> {
            events.add(new EventsDto(event.getEventName(),event.getEventStartTime(),event.getEventEndTime(),event.getQuota(),event.getLatitude(),event.getLongitude()));
        });
        return events;
    }

    public MessageResponse deleteEvent(String eventName) {
        var deletedEvent = eventsRepository.findByEventName(eventName);
        if(deletedEvent.getEventStartTime().isAfter(LocalDateTime.now())) {
            eventsRepository.delete(deletedEvent);
            return new MessageResponse("Successfully deleted event",MessageType.SUCCESS);

        }
        return new MessageResponse("Can't delete a past event",MessageType.ERROR);
    }

    public List<EventsDto> getEventsUser() {
        var events = new ArrayList<EventsDto>();
        eventsRepository.findAll().forEach(event -> {
            if(event.getEventStartTime().isAfter(LocalDateTime.now()))
            events.add(new EventsDto(event.getEventName(),event.getEventStartTime(),event.getEventEndTime(),event.getQuota(),event.getLatitude(),event.getLongitude()));
        });
        return events;
    }


    public EventsDto getEvent(String eventName) {
        return eventsMapper.mapToEventsDto(eventsRepository.findByEventName(eventName));
    }

    public List<EventsWithApplicantsCount> getApplicantsCount() {
        var eventsWithApplicantsCount = new ArrayList<EventsWithApplicantsCount>();
        eventsRepository.findAll().forEach(event -> {
            eventsWithApplicantsCount.add(new EventsWithApplicantsCount(event.getEventName(),event.getParticipants().stream().count()));
        });
        return eventsWithApplicantsCount;
    }

}
