package fr.pelliculum.restapi.list;

import fr.pelliculum.restapi.entities.List;
import fr.pelliculum.restapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ListRepository extends JpaRepository<List, Long> {

    @Query("SELECT l FROM List l WHERE l.user.username = :username")
    Optional<List> findByUsername(String username);

    @Query("SELECT l FROM List l WHERE l.user.id = :userId")
    Optional<List> findByUserId(Long userId);

    @Query("SELECT l FROM List l WHERE l.isPublic = :isPublic")
    Optional<java.util.List<List>> findPublic(Boolean isPublic);

    @Query("SELECT l FROM List l WHERE l.isPublic = :isPublic AND l.user = :user")
    Optional<java.util.List<List>> findByUserId(boolean isPublic, User user);

    @Query("SELECT COUNT(l) FROM List l WHERE l.user.id = :userId")
    Optional<Long> countByUserId(Long userId);

}
