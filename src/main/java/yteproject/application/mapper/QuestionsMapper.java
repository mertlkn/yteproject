package yteproject.application.mapper;

import org.mapstruct.Mapper;
import yteproject.application.dtos.EventsDto;
import yteproject.application.dtos.QuestionsDto;
import yteproject.application.entities.Events;
import yteproject.application.entities.Questions;

import java.util.List;

@Mapper(componentModel="spring")
public interface QuestionsMapper {
    Questions mapToQuestions(QuestionsDto questionsDto);
    QuestionsDto mapToQuestionsDto(Questions questions);
    List<Questions> mapToQuestions(List<QuestionsDto> questionsDto);
    List<QuestionsDto> mapToQuestionsDto(List<Questions> questions);
}
