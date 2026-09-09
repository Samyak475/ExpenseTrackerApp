package com.codeartist.authservice.repositories;

import org.antlr.v4.runtime.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeartist.authservice.entities.Tokens;

import javax.swing.text.html.Option;
import java.util.Optional;


@Repository
public interface TokenRepo extends JpaRepository<Tokens, String> {
    public  Tokens getTokensByTokenId(String tokenId);
}
