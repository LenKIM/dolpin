package com.great.deploy.dolpin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.AccountAdapter;
import com.great.deploy.dolpin.account.AccountRole;
import com.great.deploy.dolpin.common.AppProperties;
import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.dto.AccessToken;
import com.great.deploy.dolpin.dto.AccountWithToken;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.exception.NonAuthorizationException;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.dto.model.Provider;
import com.great.deploy.dolpin.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
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

    public Set<Favorite> getFavorite(Integer accountId) {
        return accountRepository.findById(accountId).map(
                Account::getFavorites
        ).orElseThrow(() -> new NonAuthorizationException("not found accountId: " + accountId));
    }

    public static String getOauthId(String email, Provider snsType, String snsId) {
        String oauthId;
        if (email.equals("")) {
            oauthId = snsType + snsId;
        } else {
            oauthId = email;
        }
        return oauthId;
    }

    @Transactional
    public Account saveAccount(Account account) {
        account.setPassword(this.passwordEncoder.encode(account.getPassword()));
        return this.accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Account account) {
        return this.accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByOauthId(username);
        Account.validateAccount(account);
        return new AccountAdapter(account);
    }

    public AccountWithToken createUser(String email, String nickname, Set<Favorite> favorites, Provider snsType, String snsId) {

        final Set<AccountRole> adminRoles = new HashSet<>();
        adminRoles.add(AccountRole.USER);

        if(!Provider.isContain(snsType)){
            throw new ResourceNotFoundException("허가된 SNS 로그인이 아닙니다.");
        }

        String oauthId = getOauthId(email, snsType, snsId);

        String password = "password";

        Account admin = Account.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .roles(adminRoles)
                .favorites(favorites)
                .snsType(snsType)
                .snsId(snsId)
                .oauthId(oauthId)
                .build();

        Account account = saveAccount(admin);
        return getAccessToken(account);
    }

    public AccountWithToken login(String email, Provider snsType, String snsId) {
        String oauthId = AccountService.getOauthId(email, snsType, snsId);
        Account account = accountRepository.findByOauthId(oauthId);
        return getAccessToken(account);
    }

    private AccountWithToken getAccessToken(Account account) {
        AccessToken accessToken = Optional.of(getAuthToken(account)).orElseThrow(() -> new ResourceNotFoundException("Not found Account"));
        return new AccountWithToken(account, accessToken);
    }

    private AccessToken getAuthToken(Account account) {
        Account.validateAccount(account);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic bXlBcHA6cGFzcw==");
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        String authURL = appProperties.getBaseUrl() + "/oauth/token?grant_type=password&username=" + account.getOauthId() + "&password=password";
        ResponseEntity<String> response = restTemplate.postForEntity(authURL, entity, String.class);

        AccessToken accountWithToken;
        try {
            accountWithToken = objectMapper.readValue(response.getBody(), AccessToken.class);
        } catch (Exception e) {
            throw new BadRequestException("accessToken not valid");
        }
        return accountWithToken;
    }

    public boolean checkDuplicatedNickName(String nickname){
        if(nickname == null){
            throw new BadRequestException("닉네임이 Null 값입니다.");
        }
        return accountRepository.existsByOauthId(nickname);
    }
}

