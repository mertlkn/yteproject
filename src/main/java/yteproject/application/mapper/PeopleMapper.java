package yteproject.application.mapper;

import org.mapstruct.Mapper;
import yteproject.application.dtos.PeopleDto;
import yteproject.application.entities.People;

import java.util.List;

@Mapper(componentModel="spring")
public interface PeopleMapper {
    People mapToPeople(PeopleDto PeopleDto);
    PeopleDto mapToPeopleDto(People people);
    List<People> mapToPeople(List<PeopleDto> PeopleDto);
    List<PeopleDto> mapToPeopleDto(List<People> People);
}
