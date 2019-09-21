package com.great.deploy.dolpin.account;

import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.dto.AccountResponse;
import com.great.deploy.dolpin.dto.AccountUpdateRequest;
import com.great.deploy.dolpin.exception.OAuth2AuthenticationProcessingException;
import com.great.deploy.dolpin.model.Provider;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    public static Account of(Account oldAccount, AccountUpdateRequest newAccount) {
        return new Account(
                oldAccount.getId(),
                oldAccount.getPassword(),
                oldAccount.getEmail(),
                oldAccount.getRoles(),
                oldAccount.getName(),
                oldAccount.getImageUrl(),
                newAccount.getNickname(),
                newAccount.getActiveRegion(),
                newAccount.getMedal(),
                newAccount.getDuckLevel(),
                oldAccount.getSnsType(),
                oldAccount.getSnsId(),
                oldAccount.getOauthId(),
                oldAccount.getFavorites(),
                oldAccount.getCreatedAt(),
                LocalDateTime.now()
                );
    }

    public static Account saveFavorites(Account account, Set<Favorite> favorite) {
        return new Account(account.getId(),
                account.getPassword(),
                account.getEmail(),
                account.getRoles(),
                account.getName(),
                account.getImageUrl(),
                account.getNickname(),
                account.getActiveRegion(),
                account.getMedal(),
                account.getDuckLevel(),
                account.getSnsType(),
                account.getSnsId(),
                account.getOauthId(),
                favorite,
                account.getCreatedAt(),
                LocalDateTime.now()
        );
    }

    public static AccountResponse ofResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getEmail(),
                account.getName(),
                account.getImageUrl(),
                account.getNickname(),
                account.getActiveRegion(),
                account.getMedal(),
                account.getDuckLevel(),
                account.getFavorites()
        );
    }

    public static void validateAccount(Account account) {
        if (account == null) {
            throw new OAuth2AuthenticationProcessingException("Need Check Account Information");
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String password;

    @Column(unique = true, length = 1200)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

    private String name;

    private String imageUrl;

    private String nickname;

    private String activeRegion;

    private String medal;

    private String duckLevel;

    @Enumerated(EnumType.STRING)
    private Provider snsType;
    private String snsId;

    private String oauthId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "account_favorite", joinColumns = @JoinColumn(name = "account_id"))
    private Set<Favorite> favorites = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
