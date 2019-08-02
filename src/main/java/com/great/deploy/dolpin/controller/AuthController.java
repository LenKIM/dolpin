package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.dto.ApiResponse;
import com.great.deploy.dolpin.dto.AuthResponse;
import com.great.deploy.dolpin.dto.LoginRequest;
import com.great.deploy.dolpin.dto.SignUpRequest;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.model.Accounts;
import com.great.deploy.dolpin.model.AuthProvider;
import com.great.deploy.dolpin.repository.AccountsRepository;
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
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountsRepository accountsRepository;

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
        if (accountsRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating accounts's account
        Accounts accounts = new Accounts();
        accounts.setName(signUpRequest.getName());
        accounts.setEmail(signUpRequest.getEmail());
        accounts.setPassword(signUpRequest.getPassword());
        accounts.setProvider(AuthProvider.local);

        accounts.setPassword(passwordEncoder.encode(accounts.getPassword()));

        Accounts result = accountsRepository.save(accounts);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/accounts/me")
                .buildAndExpand(result.getId()).toUri();


        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Users registered successfully@"));
    }

}
