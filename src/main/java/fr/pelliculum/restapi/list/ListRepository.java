package fr.pelliculum.restapi.list;

import fr.pelliculum.restapi.entities.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ListRepository extends JpaRepository<List, Long> {

    @Query("SELECT l FROM List l WHERE l.user.username = :username")
    Optional<List> findByUsername(String username);

    @Query("SELECT l FROM List l WHERE l.user.id = :userId")
    Optional<List> findByUserId(Long userId);

    @Query("SELECT l FROM List l WHERE l.isPublic = :isPublic")
    Optional<List> findPublic(Boolean isPublic);

}
