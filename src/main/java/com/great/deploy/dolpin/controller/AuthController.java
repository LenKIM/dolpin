package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.model.AuthProvider;
import com.great.deploy.dolpin.model.Users;
import com.great.deploy.dolpin.dto.ApiResponse;
import com.great.deploy.dolpin.dto.AuthResponse;
import com.great.deploy.dolpin.dto.LoginRequest;
import com.great.deploy.dolpin.dto.SignUpRequest;
import com.great.deploy.dolpin.repository.UserRepository;
import com.great.deploy.dolpin.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating users's account
        Users users = new Users();
        users.setName(signUpRequest.getName());
        users.setEmail(signUpRequest.getEmail());
        users.setPassword(signUpRequest.getPassword());
        users.setProvider(AuthProvider.local);

        users.setPassword(passwordEncoder.encode(users.getPassword()));

        Users result = userRepository.save(users);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Users registered successfully@"));
    }

}
