package org.example.investimentoapi.controller;

import org.example.investimentoapi.dto.AuthRequest;
import org.example.investimentoapi.dto.AuthResponse;
import org.example.investimentoapi.model.User;
import org.example.investimentoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(authRequest.getPassword());
        return userService.saveUser(user, authRequest.getRoles());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        String token = userService.generateToken(authRequest.getUsername(), authRequest.getPassword());
        return new AuthResponse(token);
    }
}
