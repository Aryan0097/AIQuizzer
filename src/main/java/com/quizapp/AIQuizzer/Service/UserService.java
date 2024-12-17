package com.quizapp.AIQuizzer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quizapp.AIQuizzer.Entity.User;
import com.quizapp.AIQuizzer.Repository.UserRepository;
import com.quizapp.AIQuizzer.Request.UserLoginRequest;
import com.quizapp.AIQuizzer.Request.UserRegistrationRequest;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService; // Service to handle JWT generation and validation
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user
    public User registerUser(UserRegistrationRequest request) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email already exists.");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(newUser);
    }

    // Mock Login and Return JWT
    public String login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        // if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        //     throw new RuntimeException("Invalid credentials");
        // }

        return tokenService.createRefreshToken(user);
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }


    // public User getUserByEmail(String email) {
    //     return userRepository.findByEmail(email)
    //         .orElseThrow(() -> new RuntimeException("User not found"));
    // }
}

