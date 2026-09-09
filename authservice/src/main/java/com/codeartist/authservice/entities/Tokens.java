package com.codeartist.authservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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


    private LocalDateTime expirationTime;
    @CreationTimestamp
    private LocalDateTime createdAtTime;

}
