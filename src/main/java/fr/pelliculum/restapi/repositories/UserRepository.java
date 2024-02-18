package fr.pelliculum.restapi.repositories;

import fr.pelliculum.restapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
