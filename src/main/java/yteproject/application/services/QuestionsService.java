package yteproject.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yteproject.application.dtos.QuestionsDto;
import yteproject.application.entities.Questions;
import yteproject.application.mapper.QuestionsMapper;
import yteproject.application.messages.MessageResponse;
import yteproject.application.messages.MessageType;
import yteproject.application.repositories.EventsRepository;
import yteproject.application.repositories.QuestionsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionsService {
    private final QuestionsRepository questionsRepository;
    private final EventsRepository eventsRepository;
    private final QuestionsMapper questionsMapper;

    public MessageResponse askQuestion(String eventName, Questions questions) {
        eventsRepository.findByEventName(eventName).getQuestions().add(questions);
        questionsRepository.save(questions);
        return new MessageResponse("Successfully asked the question", MessageType.SUCCESS);
    }

    public List<QuestionsDto> getQuestions(String eventName) {
        return questionsMapper.mapToQuestionsDto(eventsRepository.findByEventName(eventName).getQuestions().stream().collect(Collectors.toList()));
    }
}
