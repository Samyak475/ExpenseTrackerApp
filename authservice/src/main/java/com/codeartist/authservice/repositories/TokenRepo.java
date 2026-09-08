package com.codeartist.authservice.repositories;

import com.codeartist.authservice.entities.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface TokenRepo extends JpaRepository<Tokens , Long> {

}
