package com.codeartist.authservice.repositories;

import com.codeartist.authservice.entities.Tokens;
import com.codeartist.authservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User>getUserByUsername(String username);
    public Optional<User>getUserByTokens(Tokens  tokens);
}
