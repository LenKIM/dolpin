package com.great.deploy.dolpin.account;

import com.great.deploy.dolpin.common.AuditEntity;
import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.dto.AccountRequest;
import com.great.deploy.dolpin.dto.AccountResponse;
import com.great.deploy.dolpin.exception.OAuth2AuthenticationProcessingException;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AuditEntity {

    public static Account of(Account oldAccount, AccountRequest newAccount) {
        return new Account(oldAccount.getId(),
                oldAccount.getPassword(),
                oldAccount.getEmail(),
                oldAccount.getRoles(),
                oldAccount.getName(),
                oldAccount.getImageUrl(),
                newAccount.getNickname(),
                newAccount.getActiveRegion(),
                newAccount.getMedal(),
                newAccount.getDuckLevel(),
                oldAccount.getFavorite());
    }

    public static Account saveFavorites(Account account, Set<Favorite> favorite) {
        return new Account(account.getId(),
                account.getEmail(),
                account.getPassword(),
                account.getRoles(),
                account.getName(),
                account.getImageUrl(),
                account.getNickname(),
                account.getActiveRegion(),
                account.getMedal(),
                account.getDuckLevel(),
                favorite
        );
    }

    public static AccountResponse ofResponse(Account account) {
        return new AccountResponse(account.getId(),
                account.getEmail(),
                account.getName(),
                account.getImageUrl(),
                account.getNickname(),
                account.getActiveRegion(),
                account.getMedal(),
                account.getDuckLevel(),
                account.getFavorite());
    }

    public static void validateAccount(Account account) {
        if (account == null) {
            throw new OAuth2AuthenticationProcessingException("Need Check Account Information");
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "account_favorite", joinColumns = @JoinColumn(name = "account_id"))
    private Set<Favorite> favorite = new HashSet<>();
}
