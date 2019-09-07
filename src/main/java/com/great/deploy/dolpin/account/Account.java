package com.great.deploy.dolpin.account;

import com.great.deploy.dolpin.dto.AccountResponse;
import com.great.deploy.dolpin.model.Favorite;
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
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    public static Account of(Integer id, Account old) {
        return new Account(id, old.getEmail(), old.getPassword(),
                old.getRoles(), old.getName(), old.getImageUrl(),
                old.getNickname(), old.getActiveRegion(), old.getMedal(),
                old.getDuckLevel(), old.getFavorite(), old.getCreatedAt(),
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

    @Column
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
