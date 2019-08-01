package com.great.deploy.dolpin.service;


import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.model.Accounts;
import com.great.deploy.dolpin.repository.AccountsRepository;
import com.great.deploy.dolpin.security.UserPrincipal;
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
    AccountsRepository accountsRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Accounts users = accountsRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Users not found with email : " + email)
        );

        return UserPrincipal.create(users);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Accounts users = accountsRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Users", "id", id)
        );

        return UserPrincipal.create(users);
    }
}