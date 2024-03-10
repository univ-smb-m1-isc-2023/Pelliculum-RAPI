package fr.pelliculum.restapi.user;

import fr.pelliculum.restapi.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Update user profile picture
     * @param username {@link String} username
     */
    public void updateUserProfilePicture(String username) {
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
     * Remove follow
     * @param username {@link String} username
     * @param followUsername {@link String} followUsername
     */
    public void removeFollow(String username, String followUsername) {
        User user = userRepository.findByUsername(username).orElse(null);
        User follow = userRepository.findByUsername(followUsername).orElse(null);
        if (user != null && follow != null) {
            user.getFollows().remove(follow);
            userRepository.save(user);
        }
    }

    /**
     * Get follows
     * @param username {@link String} username
     * @return {@link List} of {@link User} follows
     */
    public List<UserDTO> getFollows(String username) {
        return userRepository.findFollowsByUsername(username);
    }

    /**
     * Get followers
     * @param username {@link String} username
     * @return {@link List} of {@link User} followers
     */
    public List<UserDTO> getFollowers(String username) {
        return userRepository.findFollowersByUsername(username);
    }

    /**
     * Get follows details by username
     * @param username {@link String} username
     * @return {@link List} of {@link UserDTO} followsDetails
     */

    public List<UserDTO> getFollowsDetailsByUsername(String username) {
        List<Object[]> results = userRepository.findFollowsDetailsByUsernameNative(username);
        List<UserDTO> followsDetails = new ArrayList<>();
        for (Object[] result : results) {
            followsDetails.add(new UserDTO(
                    (String) result[0], // lastname
                    (String) result[1], // firstname
                    (String) result[2], // username
                    (Long) result[3],   // followsCount
                    (Long) result[4],   // followersCount
                    (Boolean) true // isFollowedByCurrentUser
            ));
        }
        return followsDetails;
    }

    /**
     * Get followers details by username
     * @param username {@link String} username
     * @return {@link List} of {@link UserDTO} followersDetails
     */

    public List<UserDTO> getFollowersDetailsByUsername(String username) {
        List<Object[]> results = userRepository.findFollowersDetailsByUsernameNative(username);
        List<UserDTO> followersDetails = new ArrayList<>();
        for (Object[] result : results) {
            followersDetails.add(new UserDTO(
                    (String) result[0], // lastname
                    (String) result[1], // firstname
                    (String) result[2], // username
                    ((Number) result[3]).longValue(), // followsCount, safe cast to Number then to Long
                    ((Number) result[4]).longValue(),  // followersCount
                    (Boolean) result[5] // isFollowedByCurrentUser
            ));
        }
        return followersDetails;
    }







}
