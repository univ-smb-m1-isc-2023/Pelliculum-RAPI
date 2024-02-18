package fr.pelliculum.restapi.repositories;

import fr.pelliculum.restapi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
