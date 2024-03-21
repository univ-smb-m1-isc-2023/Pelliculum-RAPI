package fr.pelliculum.restapi.user;

import fr.pelliculum.restapi.entities.Review;
import fr.pelliculum.restapi.entities.User;
import fr.pelliculum.restapi.configuration.exceptions.UserNotFoundException;
import fr.pelliculum.restapi.configuration.handlers.Response;
import fr.pelliculum.restapi.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final FileStorageService fileStorageService;


    /**
     * Get an user by username or throw an exception (404)
     * @param username {@link String} username
     * @return {@link User} user
     */
    public User findByUsernameOrNotFound(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    /**
     * Get a user by username
     * @param username {@link String} username
     * @return {@link User} user
     */
    public ResponseEntity<Object> getUserByUsername(String username) {
        return Response.ok("User successfully founded !", findByUsernameOrNotFound(username));
    }

    /**
     * Update user
     * @param username {@link String} username
     * @param values   {@link User} new values
     * @return {@link User} user
     */
    public ResponseEntity<Object> updateUser(String username, User values) {
        User user = findByUsernameOrNotFound(username);
        user.setFirstname(values.getFirstname());
        user.setLastname(values.getLastname());
        user.setEmail(values.getEmail());
        user.setUsername(values.getUsername());
        userRepository.save(user);
        return Response.ok("User successfully updated !");
    }


    /**
     * Update user profile picture
     * @param username {@link String} username
     */
    public ResponseEntity<Object> updateUserProfilePicture(String username, MultipartFile file) throws IOException {
        try {
            User user = findByUsernameOrNotFound(username);
            fileStorageService.storeFile(file, username);
            user.setProfilePicturePath(uploadDir + "/" + username + ".jpeg"); // Assurez-vous que cela correspond à votre logique de résolution de chemin
            userRepository.save(user);
            return Response.ok("Profile picture successfully updated !", user);
        } catch (IOException e) {
            return Response.error("Error while updating profile picture : " + e.getMessage());
        }
    }



    /**
     * Add follow
     * @param username       {@link String} username
     * @param followUsername {@link String} followUsername
     */
    public ResponseEntity<Object> addFollow(String username, String followUsername) {
        User user = findByUsernameOrNotFound(username);
        User follow = findByUsernameOrNotFound(followUsername);
        if (user.getFollows().contains(follow)) {
            return Response.error("Vous suivez déjà cet utilisateur !");
        }
        if (username.equals(followUsername)) {
            return Response.error("Vous ne pouvez pas suivre vous même !");
        }
        user.getFollows().add(follow);
        userRepository.save(user);


        List<Object[]> network = userRepository.findFollowingAndFollowersByUsername(followUsername);
        UserDTO followUserDTO = new UserDTO(
                follow.getLastname(),
                follow.getFirstname(),
                follow.getUsername(),
                ((Number) network.get(0)[0]).longValue(), // followsCount, safe cast to Number then to Long
                ((Number) network.get(0)[1]).longValue(), // followersCount
                true // isFollowedByCurrentUser
        );

        return Response.ok("Vous suivez maintenant " + followUsername + " !", followUserDTO);
    }

    /**
     * Remove follow
     * @param username       {@link String} username
     * @param followUsername {@link String} followUsername
     */
    public ResponseEntity<Object> removeFollow(String username, String followUsername) {
        User user = findByUsernameOrNotFound(username);
        User follow = findByUsernameOrNotFound(followUsername);
        if (!user.getFollows().contains(follow)) {
            return Response.error("Vous ne suivez pas cet utilisateur !");
        }
        user.getFollows().remove(follow);
        return Response.ok("Vous ne suivez plus " + followUsername + " !", userRepository.save(user));
    }

    /**
     * Get follows
     * @param username {@link String} username
     * @return {@link List} of {@link User} follows
     */
    public ResponseEntity<Object> getFollows(String username) {
        return Response.ok("Follows successfully founded !", userRepository.findFollowsByUsername(username));
    }

    /**
     * Get followers
     * @param username {@link String} username
     * @return {@link List} of {@link User} followers
     */
    public ResponseEntity<Object> getFollowers(String username) {
        return Response.ok("Followers successfully founded !", userRepository.findFollowersByUsername(username));
    }

    /**
     * Get follows details by username
     * @param username {@link String} username
     * @return {@link List} of {@link UserDTO} followsDetails
     */

    public ResponseEntity<Object> getFollowsDetailsByUsername(String username) {
        List<Object[]> results = userRepository.findFollowsDetailsByUsernameNative(username);
        List<UserDTO> followsDetails = new ArrayList<>();
        for (Object[] result : results) {
            followsDetails.add(new UserDTO(
                    (String) result[0], // lastname
                    (String) result[1], // firstname
                    (String) result[2], // username
                    (Long) result[3],   // followsCount
                    (Long) result[4],   // followersCount
                    true // isFollowedByCurrentUser
            ));
        }
        return Response.ok("Follows details successfully founded !", followsDetails);
    }

    /**
     * Get followers details by username
     * @param username {@link String} username
     * @return {@link List} of {@link UserDTO} followersDetails
     */

    public ResponseEntity<Object> getFollowersDetailsByUsername(String username) {
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
        return Response.ok("Followers details successfully founded !", followersDetails);
    }

    @Transactional
    public ResponseEntity<Object> addReviewToUser(String username, Review review) {
        User user = findByUsernameOrNotFound(username);

        if (reviewRepository.existsByUser_IdAndMovieId(user.getId(), review.getMovieId())) {
            return Response.error("Vous avez déjà ajouté une critique pour ce film !");
        }

        review.setUser(user);
        reviewRepository.save(review);
        return Response.ok("Review successfully added !", review);
    }


}
