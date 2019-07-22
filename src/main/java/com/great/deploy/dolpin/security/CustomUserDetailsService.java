package com.great.deploy.dolpin.security;


import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.model.Users;
import com.great.deploy.dolpin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Users not found with email : " + email)
        );

        return UserPrincipal.create(users);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Users users = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Users", "id", id)
        );

        return UserPrincipal.create(users);
    }
}