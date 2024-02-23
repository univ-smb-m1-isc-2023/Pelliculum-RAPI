package fr.pelliculum.restapi.services;

import fr.pelliculum.restapi.entities.User;
import fr.pelliculum.restapi.enums.Role;
import fr.pelliculum.restapi.repositories.UserRepository;
import fr.pelliculum.restapi.requests.LoginRequest;
import fr.pelliculum.restapi.requests.RegisterRequest;
import fr.pelliculum.restapi.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
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
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        final User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        final String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

}
