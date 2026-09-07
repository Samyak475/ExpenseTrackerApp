package com.codeartist.authservice.repositories;

import com.codeartist.authservice.entities.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<Tokens , Long> {

}
