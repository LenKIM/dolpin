package com.great.deploy.dolpin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.AccountAdapter;
import com.great.deploy.dolpin.account.AccountRole;
import com.great.deploy.dolpin.common.AppProperties;
import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.dto.AccessToken;
import com.great.deploy.dolpin.model.Provider;
import com.great.deploy.dolpin.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppProperties appProperties;

    @Autowired
    ObjectMapper objectMapper;


    @Transactional
    public Account saveAccount(Account account) {
        account.setPassword(this.passwordEncoder.encode(account.getPassword()));
        return this.accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByOauthId(username);
        Account.validateAccount(account);
        return new AccountAdapter(account);
    }

    public AccessToken create(String email, String nickname, Set<Favorite> favorites, Provider SNSType, String SNSId) {

        final Set<AccountRole> adminRoles = new HashSet<>();
        adminRoles.add(AccountRole.USER);
        String OAuthId;
        if (email.isEmpty()) {
            OAuthId = SNSType + SNSId;
        } else {
            OAuthId = email;
        }

        String password = "password";
        Account admin = Account.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .roles(adminRoles)
                .favorites(favorites)
                .snsType(SNSType)
                .snsId(SNSId)
                .oauthId(OAuthId)
                .build();

        Account account = saveAccount(admin);
        return getAccessToken(account);
    }

    public AccessToken login(String oauthId) {

        Account account = accountRepository.findByOauthId(oauthId);
        return getAccessToken(account);
    }

    private AccessToken getAccessToken(Account account) {
        Account.validateAccount(account);
        AccessToken accessToken = getAuthToken(account);
        return new AccessToken(account, accessToken.getAccessToken(), accessToken.getTokenType(), accessToken.getRefreshToken(), accessToken.getExpiresIn(), accessToken.getScope());
    }

    private AccessToken getAuthToken(Account account) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic bXlBcHA6cGFzcw==");
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        String authURL = appProperties.getBaseUrl() + "/oauth/token?grant_type=password&username=" + account.getEmail() + "&password=password";
        ResponseEntity<String> response = restTemplate.postForEntity(authURL, entity, String.class);

        AccessToken accessToken = null;
        try {
            accessToken = objectMapper.readValue(response.getBody(), AccessToken.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }
}

