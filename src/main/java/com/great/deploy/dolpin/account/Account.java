package com.great.deploy.dolpin.account;

import com.great.deploy.dolpin.dto.AccountRequest;
import com.great.deploy.dolpin.dto.AccountResponse;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.domain.Favorite;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    public static Account of(Account oldAccount, AccountRequest newAccount) {
        return new Account(oldAccount.getId(),
                oldAccount.getEmail(),
                oldAccount.getPassword(),
                oldAccount.getRoles(),
                oldAccount.getName(),
                oldAccount.getImageUrl(),
                newAccount.getNickname(),
                newAccount.getActiveRegion(),
                newAccount.getMedal(),
                newAccount.getDuckLevel(),
                oldAccount.getFavorite(),
                oldAccount.getCreatedAt(),
                LocalDateTime.now());
    }

    public static Account saveFavorites(Account account, Set<Favorite> favorite) {
        return new Account(account.getId(), account.getEmail(), account.getPassword(),
                account.getRoles(), account.getName(), account.getImageUrl(),
                account.getNickname(), account.getActiveRegion(), account.getMedal(),
                account.getDuckLevel(), favorite, account.getCreatedAt(), LocalDateTime.now());
    }

    public static AccountResponse ofResponse(Account account) {
        return new AccountResponse(account.getId(), account.getEmail(), account.getName(),
                account.getImageUrl(), account.getNickname(), account.getActiveRegion(),
                account.getMedal(), account.getDuckLevel(), account.getFavorite());
    }

    public static void validateAccount(Account account) {
        if (account == null) {
            throw new BadRequestException("No found user");
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String password;

    @Column(unique = true)
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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
