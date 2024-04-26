package fr.pelliculum.restapi.user;


import fr.pelliculum.restapi.configuration.exceptions.UserNotFoundException;
import fr.pelliculum.restapi.configuration.handlers.Response;
import fr.pelliculum.restapi.entities.Movie;
import fr.pelliculum.restapi.entities.Review;
import fr.pelliculum.restapi.entities.User;
import fr.pelliculum.restapi.list.ListRepository;
import fr.pelliculum.restapi.review.ReviewDTO;
import fr.pelliculum.restapi.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.ByteArrayBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final FileStorageService fileStorageService;
    private final ListRepository listRepository;



    /**
     * Get an user by username or throw an exception (404)
     *
     * @param username {@link String} username
     * @return {@link User} user
     */
    public User findByUsernameOrNotFound(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    /**
     * Get a user by username
     *
     * @param username {@link String} username
     * @return {@link User} user
     */
    @Transactional
    public ResponseEntity<Object> getUserByUsername(String username) {
        return Response.ok("User successfully founded !", findByUsernameOrNotFound(username));
    }

    /**
     * Update user
     *
     * @param username {@link String} username
     * @param values   {@link User} new values
     * @return {@link User} user
     */
    @Transactional
    public ResponseEntity<Object> updateUser(String username, User values) {
        User user = findByUsernameOrNotFound(username);
        user.setFirstname(values.getFirstname());
        user.setLastname(values.getLastname());
        user.setEmail(values.getEmail());
        user.setUsername(values.getUsername());
        userRepository.save(user);
        return Response.ok("User successfully updated !", user);
    }


    /**
     * Update user profile picture
     *
     * @param username {@link String} username
     */
    @Transactional
    public ResponseEntity<Object> updateUserProfilePicture(String username, MultipartFile file) throws IOException {
        try {
            User user = findByUsernameOrNotFound(username);
            // Créez une instance HttpClient
            // Créez une instance CloseableHttpClient
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                // Créez une requête POST
                HttpPost httpPost = new HttpPost("http://109.122.198.32:3090/profile-picture/" + username);

                // Convertir MultipartFile en byte[]
                byte[] fileBytes = file.getBytes();

                // Créez une entité multipart/form-data
                HttpEntity multipartEntity = MultipartEntityBuilder.create()
                        .addPart("file", new ByteArrayBody(fileBytes, ContentType.DEFAULT_BINARY, file.getOriginalFilename()))
                        .build();

                // Ajoutez l'entité multipart à la requête POST
                httpPost.setEntity(multipartEntity);

                // Exécutez la requête et obtenez la réponse
                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    // Obtenez le corps de la réponse
                    HttpEntity responseEntity = response.getEntity();
                    String responseBody = EntityUtils.toString(responseEntity);

                    // Affichez la réponse
                    System.out.println("Response: " + responseBody);
                    user.setProfilePicture("http://109.122.198.32:3090/profilePictures/"+username+".jpeg");
                    // Libérez les ressources associées à la réponse
                    EntityUtils.consume(responseEntity);
                    userRepository.save(user);
                    return Response.ok("Profile picture successfully updated !", user);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
//            fileStorageService.storeFile(file, username);
//            user.setProfilePicturePath(uploadDir + "/" + username + ".jpeg"); // Assurez-vous que cela correspond à votre logique de résolution de chemin
        } catch (IOException e) {
            return Response.error("Error while updating profile picture : " + e.getMessage());
        }
    }


    /**
     * Add follow
     *
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

        Map<String, Object> rowMap = new LinkedHashMap<>();
        Long followsCount = ((Number) network.get(0)[0]).longValue(); // followsCount, safe cast to Number then to Long
        Long followersCount = ((Number) network.get(0)[1]).longValue(); // followersCount
        rowMap.put("followsCount", followsCount);
        rowMap.put("followersCount", followersCount);
        rowMap.put("listCount", listRepository.countByUserId(Long.valueOf(user.getId())));
        rowMap.put("reviewCount", reviewRepository.countByUserId(Long.valueOf(user.getId())));
        rowMap.put("likeCount", userRepository.countReviewLikesById(Long.valueOf(user.getId())));
        List<ReviewDTO> reviews = reviewRepository.findReviewDTOsByUsername(username);
        rowMap.put("reviewsCount", reviews.size());
        User userReturn = findByUsernameOrNotFound(followUsername);
        rowMap.put("user", userReturn);

        return Response.ok("Vous suivez maintenant " + followUsername + " !", rowMap);

    }

    /**
     * Remove follow
     *
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
     *
     * @param username {@link String} username
     * @return {@link List} of {@link User} follows
     */
    @Transactional
    public ResponseEntity<Object> getFollows(String username) {
        return Response.ok("Follows successfully founded !", userRepository.findFollowsByUsername(username));
    }

    /**
     * Get followers
     *
     * @param username {@link String} username
     * @return {@link List} of {@link User} followers
     */
    public ResponseEntity<Object> getFollowers(String username) {
        return Response.ok("Followers successfully founded !", userRepository.findFollowersByUsername(username));
    }


    /**
     * Get follows details by username
     *
     * @param username {@link String} username
     * @return {@link List} of {@link UserDTO} followsDetails
     */
    @Transactional
    public ResponseEntity<Object> getFollowsDetailsByUsername(String username) {
        List<Object[]> results = userRepository.findFollowsDetailsByUsernameNative(username);


        // Convert the list of arrays to a list of maps
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> rowMap = new LinkedHashMap<>();
            String e = (String) row[0];
            Long followsCount = ((Number) row[1]).longValue(); // Convertit en Long
            Long followersCount = ((Number) row[2]).longValue(); // Convertit en Long
            rowMap.put("followsCount", followsCount);
            rowMap.put("followersCount", followersCount);
            User user = findByUsernameOrNotFound(e);
            rowMap.put("listCount", listRepository.countByUserId(Long.valueOf(user.getId())));
            rowMap.put("reviewCount", reviewRepository.countByUserId(Long.valueOf(user.getId())));
            rowMap.put("likeCount", userRepository.countReviewLikesById(Long.valueOf(user.getId())));
            rowMap.put("user", user);
            resultList.add(rowMap);
        }



        return Response.ok("Follows details successfully founded !", resultList);
    }


    /**
     * Get followers details by username
     *
     * @param username {@link String} username
     * @return {@link List} of {@link UserDTO} followersDetails
     */
    @Transactional
    public ResponseEntity<Object> getFollowersDetailsByUsername(String username) {
        List<Object[]> results = userRepository.findFollowersDetailsByUsernameNative(username);

        // Convert the list of arrays to a list of maps
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> rowMap = new LinkedHashMap<>();
            String e = (String) row[0];
            Long followsCount = ((Number) row[1]).longValue(); // Convertit en Long
            Long followersCount = ((Number) row[2]).longValue(); // Convertit en Long
            List<ReviewDTO> reviews = reviewRepository.findReviewDTOsByUsername(e);
            rowMap.put("reviewsCount", reviews.size());
            rowMap.put("followsCount", followsCount);
            rowMap.put("followersCount", followersCount);
            User user = findByUsernameOrNotFound(e);
            rowMap.put("listCount", listRepository.countByUserId(Long.valueOf(user.getId())));
            rowMap.put("reviewCount", reviewRepository.countByUserId(Long.valueOf(user.getId())));
            rowMap.put("likeCount", userRepository.countReviewLikesById(Long.valueOf(user.getId())));
            rowMap.put("user", user);
            resultList.add(rowMap);
        }
        return Response.ok("Followers details successfully founded !", resultList);
    }


    /**
     * Get watchlist by username
     *
     * @param username {@link String} username
     * @return {@link List} of {@link Movie} watchlist
     */
    public ResponseEntity<Object> getWatchlist(String username) {
        return Response.ok("Watchlist successfully founded !", findByUsernameOrNotFound(username).getWatchlist());
    }

    /**
     * Add movie to watchlist
     *
     * @param username {@link String} username
     * @param movieId  {@link Long} movieId
     */
    public ResponseEntity<Object> addMovieToWatchlist(String username, Long movieId) {
        User user = findByUsernameOrNotFound(username);
        user.getWatchlist().add(movieId);
        return Response.ok("Le film a bien été ajouté à votre watchlist", userRepository.save(user));
    }

    /**
     * Remove movie from watchlist
     *
     * @param username {@link String} username
     * @param movieId  {@link Long} movieId
     */
    public ResponseEntity<Object> removeMovieFromWatchlist(String username, Long movieId) {
        User user = findByUsernameOrNotFound(username);
        user.getWatchlist().remove(movieId);
        return Response.ok("Le film a bien été retiré de votre watchlist", userRepository.save(user));
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
