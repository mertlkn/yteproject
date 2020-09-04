package yteproject.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yteproject.application.dtos.PeopleDto;
import yteproject.application.entities.Events;
import yteproject.application.entities.People;
import yteproject.application.mapper.PeopleMapper;
import yteproject.application.messages.MessageResponse;
import yteproject.application.messages.MessageType;
import yteproject.application.repositories.EventsRepository;
import yteproject.application.repositories.PeopleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeopleService {
    private final EventsRepository eventsRepository;
    private final PeopleRepository peopleRepository;
    private final PeopleMapper peopleMapper;

    public MessageResponse applyUser(PeopleDto peopleDto,String eventName) {
//        if(peopleRepository.findByTcKimlikNo(peopleDto.getTcKimlikNo()) != null) {
//            peopleRepository.findByTcKimlikNo(peopleDto.getTcKimlikNo()).getAppliedEvents().add(eventsRepository.findByEventName(eventName));
//            return peopleDto;
//        }
//        else {
//            var newset = new HashSet<Events>();
//            newset.add(eventsRepository.findByEventName(eventName));
//            People people = new People(peopleDto.getName(),peopleDto.getSurname(), peopleDto.getTcKimlikNo(),newset);
//            peopleRepository.save(people);
//            return peopleDto;
//        }
        boolean tcIsUnique=eventsRepository.findByEventName(eventName).getParticipants().stream().noneMatch(people -> people.getTcKimlikNo().equals(peopleDto.getTcKimlikNo()));
        if(tcIsUnique) {
            Events event=eventsRepository.findByEventName(eventName);
            if(event.getQuota()==0) {
                return new MessageResponse("Quota is full!", MessageType.ERROR);
            }
            event.setQuota(event.getQuota()-1);
            People people = peopleMapper.mapToPeople(peopleDto);
            people.getAppliedEvents().add(event);
            peopleRepository.save(people);
            return new MessageResponse("Successfully applied", MessageType.SUCCESS);
        }
        else {
            return new MessageResponse("TC Kimlik No is not unique", MessageType.ERROR);
        }

    }

    public List<PeopleDto> getApplicants(String eventName) {
        return peopleMapper.mapToPeopleDto(eventsRepository.findByEventName(eventName).getParticipants().stream().collect(Collectors.toList()));
    }
}
