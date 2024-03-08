package fr.pelliculum.restapi.user;

import fr.pelliculum.restapi.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UserRepository userRepository;

    /**
     * Get a user by username
     * @param username {@link String} username
     * @return {@link User} user
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * Update user
     * @param username {@link String} username
     * @param user {@link User} user
     * @return {@link User} user
     */
    public User updateUser(String username, User user) {
        User existingUser = userRepository.findByUsername(username).orElse(null);
        assert existingUser != null;
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());
        return userRepository.save(existingUser);
    }

    public void updateUserProfilePicture(String username, String fileName) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            user.setProfilePicturePath(uploadDir + "/" + username + ".jpeg"); // Assurez-vous que cela correspond à votre logique de résolution de chemin
            userRepository.save(user);
        }
    }

    /**
     * Add follow
     * @param username {@link String} username
     * @param followUsername {@link String} followUsername
     */
    public void addFollow(String username, String followUsername) {
        User user = userRepository.findByUsername(username).orElse(null);
        User follow = userRepository.findByUsername(followUsername).orElse(null);
        if (user != null && follow != null) {
            user.getFollows().add(follow);
            userRepository.save(user);
        }
    }

    /**
     * Get follows
     * @param username {@link String} username
     * @return {@link List} of {@link User} follows
     */
    public List<UserDTO> getFollows(String username) {
        return userRepository.findFollowsByUserName(username);
    }

    /**
     * Get followers
     * @param username {@link String} username
     * @return {@link List} of {@link User} followers
     */
    public List<UserDTO> getFollowers(String username) {

        return userRepository.findFollowersByUserName(username);
    }





}
