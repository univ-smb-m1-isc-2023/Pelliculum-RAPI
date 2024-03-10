package fr.pelliculum.restapi.user;

import fr.pelliculum.restapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String email);

    @Query("SELECT new fr.pelliculum.restapi.user.UserDTO(u.lastname, u.firstname, u.username) FROM User u JOIN u.follows f WHERE f.username = :username")
    List<UserDTO> findFollowersByUserName(String username);

    @Query("SELECT new fr.pelliculum.restapi.user.UserDTO(f.lastname, f.firstname, f.username) FROM User u JOIN u.follows f WHERE u.username = :username")
    List<UserDTO> findFollowsByUserName(String username);

    @Query(value = "SELECT u.lastname, u.firstname, u.username, " +
            "(SELECT COUNT(*) FROM users_follows WHERE user_id = f.follows_id) AS followsCount, " +
            "(SELECT COUNT(*) FROM users_follows WHERE follows_id = f.follows_id) AS followersCount " +
            "FROM users u " +
            "INNER JOIN users_follows f ON u.id = f.follows_id " +
            "WHERE f.user_id = (SELECT id FROM users WHERE username = :username)",
            nativeQuery = true)
    List<Object[]> findFollowsDetailsByUsernameNative(@Param("username") String username);

    @Query(value = "SELECT u.lastname, u.firstname, u.username, " +
            "(SELECT COUNT(*) FROM users_follows WHERE user_id = f.user_id) AS followsCount, " +
            "(SELECT COUNT(*) FROM users_follows WHERE follows_id = f.user_id) AS followersCount " +
            "FROM users u " +
            "INNER JOIN users_follows f ON u.id = f.user_id " +
            "WHERE f.follows_id = (SELECT id FROM users WHERE username = :username)",
            nativeQuery = true)
    List<Object[]> findFollowersDetailsByUsernameNative(@Param("username") String username);


    Boolean existsByEmail(String email);
}
