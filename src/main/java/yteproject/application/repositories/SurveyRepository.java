package yteproject.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yteproject.application.entities.Survey;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long> {
}
