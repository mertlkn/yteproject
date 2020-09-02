package yteproject.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yteproject.application.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}
