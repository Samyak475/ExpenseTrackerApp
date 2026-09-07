package com.codeartist.authservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tokens {
    @Id
    private String tokenId;
    @OneToOne
    private User user;
    @UpdateTimestamp
    private LocalDateTime expirationTime;
    @CreationTimestamp
    private LocalDateTime createdAtTime;

}
