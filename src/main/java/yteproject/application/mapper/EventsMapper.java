package yteproject.application.mapper;

import org.mapstruct.Mapper;
import yteproject.application.dtos.EventsDto;
import yteproject.application.entities.Events;

import java.util.List;

@Mapper(componentModel="spring")
public interface EventsMapper {
    Events mapToEvents(EventsDto eventsDto);
    EventsDto mapToEventsDto(Events events);
    List<Events> mapToEvents(List<EventsDto> eventsDto);
    List<EventsDto> mapToEventsDto(List<Events> events);
}
