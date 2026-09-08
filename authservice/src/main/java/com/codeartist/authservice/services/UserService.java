package com.codeartist.authservice.services;

import com.codeartist.authservice.dtos.LoginRequestDto;
import com.codeartist.authservice.dtos.LoginResponseDto;
import com.codeartist.authservice.dtos.SignUpResponseDto;
import com.codeartist.authservice.dtos.UserDto;
import com.codeartist.authservice.entities.Tokens;
import com.codeartist.authservice.entities.User;
import com.codeartist.authservice.repositories.TokenRepo;
import com.codeartist.authservice.repositories.UserRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {



    @Autowired
    UserRepo userRepo;

    @Autowired
    TokenRepo tokenRepo;



    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.getUserByUsername(username);
        return user.orElseThrow( ()->new UsernameNotFoundException("User not present.Pls signUp"));
    }

    public UserDto getUserByUsername(String username){
        Optional<User>optionalUser= userRepo.getUserByUsername(username);
        UserDto userDto = new UserDto();
        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("User not present.Pls signUp");;
        return userDto.getUserDtoFromEntity(optionalUser.get());
    }





    public void saveTokenInDb(Tokens tokens) throws  Exception{
        tokenRepo.save(tokens);
    }

    public void saveOrUpdateUserInDB(User user) throws Exception{
        userRepo.save(user);
    }


//    public Boolean isValidPassword(String rawPassword, String encrptPassword){
//        return passwordEncoder.matches(rawPassword,encrptPassword) ;
//    }





}
