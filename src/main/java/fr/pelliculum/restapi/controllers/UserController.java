package fr.pelliculum.restapi.controllers;

import fr.pelliculum.restapi.entities.User;
import fr.pelliculum.restapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users/")
public class UserController {

    private UserRepository userRepository;


    @GetMapping("")
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}
