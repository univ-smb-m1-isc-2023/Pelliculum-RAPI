package fr.pelliculum.restapi.controllers;

import fr.pelliculum.restapi.entities.Movie;
import fr.pelliculum.restapi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies/")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("")
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("")
    public Movie createMovie(Movie movie) {
        movie.setName("New Movie");
        return movieRepository.save(movie);
    }

}
