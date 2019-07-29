package com.great.deploy.dolpin.security.oauth2;

import com.great.deploy.dolpin.exception.OAuth2AuthenticationProcessingException;
import com.great.deploy.dolpin.model.Accounts;
import com.great.deploy.dolpin.model.AuthProvider;
import com.great.deploy.dolpin.repository.AccountsRepository;
import com.great.deploy.dolpin.security.UserPrincipal;
import com.great.deploy.dolpin.security.oauth2.user.OAuth2UserInfo;
import com.great.deploy.dolpin.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private AccountsRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<Accounts> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        Accounts users;
        if(userOptional.isPresent()) {
            users = userOptional.get();
            if(!users.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        users.getProvider() + " account. Please use your " + users.getProvider() +
                        " account to login.");
            }
            users = updateExistingUser(users, oAuth2UserInfo);
        } else {
            users = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(users, oAuth2User.getAttributes());
    }

    private Accounts registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Accounts users = new Accounts();

        users.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        users.setProviderId(oAuth2UserInfo.getId());
        users.setName(oAuth2UserInfo.getName());
        users.setEmail(oAuth2UserInfo.getEmail());
        users.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(users);
    }

    private Accounts updateExistingUser(Accounts existingUsers, OAuth2UserInfo oAuth2UserInfo) {
        existingUsers.setName(oAuth2UserInfo.getName());
        existingUsers.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUsers);
    }

}
