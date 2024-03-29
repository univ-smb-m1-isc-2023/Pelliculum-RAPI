package fr.pelliculum.restapi.list;

import fr.pelliculum.restapi.entities.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Long> {
}
