package com.codeartist.userservice.repository;

import com.codeartist.userservice.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo,String> {
    public Optional<UserInfo> getUserInfoByUserId(String userId);
}
