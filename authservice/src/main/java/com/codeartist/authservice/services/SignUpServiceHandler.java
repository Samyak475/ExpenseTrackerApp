package com.codeartist.authservice.services;

import com.codeartist.authservice.dtos.SignUpResponseDto;
import com.codeartist.authservice.dtos.UserDto;
import com.codeartist.authservice.entities.Tokens;
import com.codeartist.authservice.entities.User;
import com.codeartist.authservice.repositories.TokenRepo;
import com.codeartist.authservice.repositories.UserRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignUpServiceHandler implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    JWTUtilService jwtUtilService;
    @Autowired
    TokenRepo tokenRepo;

    public SignUpResponseDto signUpRequest(UserDto userDto){
        System.out.println("here for signup");
        String accessToken = jwtUtilService.generateToken(userDto);
        String refreshToken = jwtUtilService.generateToken(userDto);
        Tokens refreshTokenInDb = Tokens.builder().tokenId(refreshToken).build();
        try{
            tokenRepo.save(refreshTokenInDb);
        } catch (Exception e) {
            throw new RuntimeException("Unable to save toke in DB");
        }
        SignUpResponseDto responseDto = SignUpResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        User  userDetails = getUserFromUserDto(userDto);
        userDetails.setTokens(refreshTokenInDb);
        try {
            saveUserInDB(userDetails);
        } catch (Exception e) {
            throw new RuntimeException("Unable to save user in DB");
        }
        return responseDto;
    }

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

    public User saveUserInDB(User user){
        return userRepo.save(user);
    }
    public User getUserFromUserDto(UserDto userDto){
        return User.builder().emailId(userDto.getUserEmailId())
                .username(userDto.getUserName())
                .password(userDto.getUserEmailPassword())
                .build();
    }
}
