package com.great.deploy.dolpin.account;

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

    @ElementCollection
    @CollectionTable(name = "account_favorite", joinColumns = @JoinColumn(name = "favorite_id"))
    private Set<Favorite> favorite = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;


}
