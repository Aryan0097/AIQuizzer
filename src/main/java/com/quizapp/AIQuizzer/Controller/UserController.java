package com.quizapp.AIQuizzer.Controller;

import com.quizapp.AIQuizzer.Request.UserLoginRequest;
import com.quizapp.AIQuizzer.Request.UserRegistrationRequest;
import com.quizapp.AIQuizzer.Entity.User;
import com.quizapp.AIQuizzer.Service.TokenService;
import com.quizapp.AIQuizzer.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    // Endpoint to register a new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        User user = userService.registerUser(userRegistrationRequest);
        return ResponseEntity.ok(user.getUsername()+" registered successfully.");
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        // boolean isAuthenticated = userService.authenticateUser(userLoginRequest);

        // if (!isAuthenticated) {
        //     return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password."));
        // }

        String token =userService.login(userLoginRequest);
        return ResponseEntity.ok(Map.of("message", userLoginRequest.getUsername()+" Login successful.", "token", token));
    }

}
