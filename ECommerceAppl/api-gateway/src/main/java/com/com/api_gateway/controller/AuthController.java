package com.com.api_gateway.controller;

import com.com.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestParam String username, @RequestParam String password) {
        if ("user".equals(username) && "password".equals(password)) {
            String token = jwtUtil.generateToken(username, List.of("ROLE_USER"));
            return ResponseEntity.ok(Map.of("access_token", token, "role", "ROLE_USER"));
        } else if ("admin".equals(username) && "admin123".equals(password)) {
            String token = jwtUtil.generateToken(username, List.of("ROLE_ADMIN"));
            return ResponseEntity.ok(Map.of("access_token", token, "role", "ROLE_ADMIN"));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }
}
