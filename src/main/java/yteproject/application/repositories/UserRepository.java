package yteproject.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yteproject.application.entities.Users;

public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
}
