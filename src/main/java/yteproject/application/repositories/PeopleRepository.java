package yteproject.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yteproject.application.entities.People;
@Repository
public interface PeopleRepository extends JpaRepository<People,Long> {
    People findByTcKimlikNo(String tcKimlikNo);
}
