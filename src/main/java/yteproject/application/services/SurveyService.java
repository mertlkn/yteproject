package yteproject.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yteproject.application.dtos.SurveyDto;
import yteproject.application.entities.Survey;
import yteproject.application.messages.MessageResponse;
import yteproject.application.messages.MessageType;
import yteproject.application.repositories.EventsRepository;
import yteproject.application.repositories.SurveyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final EventsRepository eventsRepository;
    private final SurveyRepository surveyRepository;

    public SurveyDto getSurveyAverages(String eventName) {
        long count=eventsRepository.findByEventName(eventName).getSurveys().stream().count();
        if(count==0) return new SurveyDto(eventName,0.0,0.0);
        List<Survey> surveys=eventsRepository.findByEventName(eventName).getSurveys().stream().collect(Collectors.toList());
        double dates=0;
        double contents=0;
        for(Survey s:surveys) {
            dates+=s.getDate();
            contents+=s.getContent();
        }
        return new SurveyDto(eventName,dates/count,contents/count);
    }

    public MessageResponse answerSurvey(String eventName,Survey survey) {
        eventsRepository.findByEventName(eventName).getSurveys().add(survey);
        surveyRepository.save(survey);
        return new MessageResponse("Successfully answered the survey", MessageType.SUCCESS);
    }
}
