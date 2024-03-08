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
    private final FileStorageService fileStorageService;

    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping("{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(username, user));
    }

    @PostMapping("/{username}/profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable String username, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.storeFile(file, username);
            userService.updateUserProfilePicture(username, fileName); // Nous implémenterons cette méthode ensuite
            return ResponseEntity.ok().body("Image téléchargée avec succès : " + fileName);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Impossible de télécharger l'image : " + e.getMessage());
        }
    }

    @PostMapping("/{username}/follows/{followUsername}")
    public ResponseEntity<?> addFollow(@PathVariable String username, @PathVariable String followUsername) {
        User user = userService.getUserByUsername(username);
        for (User friend : user.getFollows()) {
            if (friend.getUsername().equals(followUsername)) {
                return ResponseEntity.badRequest().body("Vous suivez déjà cet utilisateur");
            }
        }

        if (username.equals(followUsername)) {
            return ResponseEntity.badRequest().body("Vous ne pouvez pas vous suivre vous-même");
        }
        if (userService.getUserByUsername(username) == null || userService.getUserByUsername(followUsername) == null) {
            return ResponseEntity.badRequest().body("L'utilisateur n'existe pas");
        }

        userService.addFollow(username, followUsername);
        return ResponseEntity.ok().body("Ami ajouté avec succès");
    }

    @GetMapping("/{username}/follows")
    public ResponseEntity<?> getFollows(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getFollows(username));
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getFollowers(username));
    }

}
