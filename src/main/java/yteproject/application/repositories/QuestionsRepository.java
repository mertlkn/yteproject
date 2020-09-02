package yteproject.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yteproject.application.entities.Questions;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions,Long> {
}
