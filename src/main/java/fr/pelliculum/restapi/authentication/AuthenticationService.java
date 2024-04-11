package fr.pelliculum.restapi.authentication;

import fr.pelliculum.restapi.entities.User;
import fr.pelliculum.restapi.enums.Role;
import fr.pelliculum.restapi.configuration.exceptions.UserNotFoundException;
import fr.pelliculum.restapi.configuration.handlers.Response;
import fr.pelliculum.restapi.user.UserRepository;
import fr.pelliculum.restapi.configuration.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
     * Get an user by username or email or throw an exception (404)
     * @param username {@link String} username
     * @param email {@link String} email
     * @return {@link User} user
     */
    public User findByUsernameOrEmailOrNotFound(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email)
                .orElseThrow(() -> new UserNotFoundException("User not found with username or email: " + email + username));
    }
    /**
     * Register a new user
     *
     * @param request {@link RegisterRequest} request
     * @return {@link ResponseEntity} response
     */
    public ResponseEntity<?> register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return Response.conflict("Username is already taken !");
        }

        final User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .username(request.getUsername())
                .build();

        userRepository.save(user);
        final String jwt = jwtService.generateToken(user);
        return Response.ok("User successfully registered !", new AuthenticationResponse(jwt, user.getUsername()));
    }

    /**
     * Login a user and generate a token
     *
     * @param request {@link LoginRequest} request
     * @return {@link AuthenticationResponse} response
     */
    public ResponseEntity<?> login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername() == null ? request.getEmail() : request.getUsername(), request.getPassword()));
            final User user = findByUsernameOrEmailOrNotFound(request.getUsername(), request.getEmail());
            final String jwt = jwtService.generateToken(user);
            return Response.ok("User successfully logged in !", new AuthenticationResponse(jwt, user.getUsername()));
        } catch (AuthenticationException e) {
            return Response.error("Mot de passe ou nom d'utilisateur incorrect !");
        }
    }

    /**
     * Check if an email exists
     *
     * @param email {@link String} email
     * @return {@link ResponseEntity} response
     */
    public ResponseEntity<?> exist(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            return Response.notFound("Email doesn't exist ! ", false);
        }
        return Response.ok("Email exists! ", true);
    }

}
