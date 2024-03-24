package fr.pelliculum.restapi.user;

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
@RequestMapping("/users/")
public class UserController {

    private final UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User user) {
        return userService.updateUser(username, user);
    }

    @PostMapping("/{username}/profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable String username, @RequestParam("file") MultipartFile file) throws IOException {
        return userService.updateUserProfilePicture(username, file);
    }

    @PostMapping("/{username}/follows/{followUsername}")
    public ResponseEntity<?> addFollow(@PathVariable String username, @PathVariable String followUsername) {
        return userService.addFollow(username, followUsername);
    }

    @DeleteMapping("/{username}/unfollows/{followUsername}")
    public ResponseEntity<?> removeFollow(@PathVariable String username, @PathVariable String followUsername) {
        return userService.removeFollow(username, followUsername);
    }

    @GetMapping("/{username}/follows")
    public ResponseEntity<?> getFollows(@PathVariable String username) {
        return userService.getFollows(username);
    }

    @GetMapping("/{username}/follows-details")
    public ResponseEntity<?> getFollowsDetails(@PathVariable String username) {
        return userService.getFollowsDetailsByUsername(username);
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable String username) {
        return userService.getFollowers(username);
    }

    @GetMapping("/{username}/followers-details")
    public ResponseEntity<?> getFollowersDetails(@PathVariable String username) {
        return userService.getFollowersDetailsByUsername(username);
    }

    @GetMapping("/{username}/watchlist")
    public ResponseEntity<?> getWatchlist(@PathVariable String username) {
        return userService.getWatchlist(username);
    }

    @PostMapping("/{username}/watchlist/{movieId}")
    public ResponseEntity<?> addMovieToWatchlist(@PathVariable String username, @PathVariable Long movieId) {
        return userService.addMovieToWatchlist(username, movieId);
    }

    @DeleteMapping("/{username}/watchlist/{movieId}")
    public ResponseEntity<?> removeMovieFromWatchlist(@PathVariable String username, @PathVariable Long movieId) {
        return userService.removeMovieFromWatchlist(username, movieId);
    }



}
