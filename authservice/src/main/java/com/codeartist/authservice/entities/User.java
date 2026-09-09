package com.codeartist.authservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
//@Table(name = "User"
//, uniqueConstraints = {@UniqueConstraint(name = "uk_user",columnNames = {"username","emailId"})}
//)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userId;
    @NonNull
    @Column(unique = true)
    private String username;
    @NonNull
    @Column(unique = true)
    private String emailId;
    private String password;
//    private Collection<GrantedAuthority> authority ;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "token_id" , referencedColumnName = "tokenId"
    )
    private Tokens tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    @NonNull
    public String getUsername() {
        return this.username;
    }
}
