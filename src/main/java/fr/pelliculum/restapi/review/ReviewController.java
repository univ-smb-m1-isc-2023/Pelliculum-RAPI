package fr.pelliculum.restapi.review;

import fr.pelliculum.restapi.entities.Review;
import fr.pelliculum.restapi.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews/")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("{movieId}")
    public ResponseEntity<?> getReviewsByMovieId(@PathVariable Long movieId) {
        return reviewService.getReviewsByMovieId(movieId);
    }

    @GetMapping("user/{username}")
    public ResponseEntity<?> getReviewsByUsername(@PathVariable String username) {
        return reviewService.getReviewsByUsername(username);
    }
    
}
