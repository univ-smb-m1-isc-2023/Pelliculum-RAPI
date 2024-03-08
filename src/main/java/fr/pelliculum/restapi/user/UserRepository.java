package fr.pelliculum.restapi.user;

import fr.pelliculum.restapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String email);

    @Query("SELECT new fr.pelliculum.restapi.user.UserDTO(u.lastname, u.firstname, u.username) FROM User u JOIN u.follows f WHERE f.username = :username")
    List<UserDTO> findFollowersByUserName(String username);

    @Query("SELECT new fr.pelliculum.restapi.user.UserDTO(f.lastname, f.firstname, f.username) FROM User u JOIN u.follows f WHERE u.username = :username")
    List<UserDTO> findFollowsByUserName(String username);

    Boolean existsByEmail(String email);
}
