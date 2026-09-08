package com.codeartist.authservice.services;

import com.codeartist.authservice.dtos.LoginRequestDto;
import com.codeartist.authservice.dtos.LoginResponseDto;
import com.codeartist.authservice.dtos.SignUpResponseDto;
import com.codeartist.authservice.dtos.UserDto;
import com.codeartist.authservice.entities.Tokens;
import com.codeartist.authservice.entities.User;
import com.codeartist.authservice.repositories.TokenRepo;
import com.codeartist.authservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    JWTUtilService jwtUtilService;
    @Autowired
    TokenRepo tokenRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;



    @Transactional
    public SignUpResponseDto signUpRequest(UserDto userDto){
        userDto.setUserEmailPassword(passwordEncoder.encode(userDto.getUserEmailPassword()));
        User newUser = getUserFromUserDto(userDto);
        System.out.println("here for signup");
        String accessToken = jwtUtilService.generateToken(userDto);
        String refreshToken = jwtUtilService.generateRefreshToken(userDto);
        Tokens refreshTokenInDb = Tokens.builder().tokenId(refreshToken).build();
        try{
//            saveTokenInDb(refreshTokenInDb);
//            tokenRepo.save(refreshTokenInDb);
            newUser.setTokens(refreshTokenInDb);
//            saveOrUpdateUserInDB(newUser);
            userRepo.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException("Unable to save token in DB"+e);
        }
        return SignUpResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }



    @Transactional
    public LoginResponseDto loginRequest(LoginRequestDto requestDto){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(),requestDto.getPassword()));
        if(!authentication.isAuthenticated()){
            throw  new RuntimeException("User not authenticated");
        }

        User existingUser = (User) authentication.getPrincipal();
        UserDto userDto =  new UserDto().getUserDtoFromEntity(existingUser);
        userDto.setUserEmailPassword(requestDto.getPassword());
        String accessToken = jwtUtilService.generateToken(userDto);
        String refreshToken = jwtUtilService.generateToken(userDto);
        Tokens refreshTokenInDb = Tokens.builder().tokenId(refreshToken).build();
        try{
//            saveTokenInDb(refreshTokenInDb);
           // tokenRepo.save(refreshTokenInDb);
            existingUser.setTokens(refreshTokenInDb);
//            saveOrUpdateUserInDB(existingUser);
            userRepo.save(existingUser);
        } catch (Exception e) {
            throw new RuntimeException("Unable to save token in DB");
        }

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .email(userDto.getUserEmailId())
                .loginStatus("User Logged In Successfully")
                .username(userDto.getUsername())
                .refreshToken(refreshToken)
                .build();
    }

    public User getUserFromUserDto(UserDto userDto){
        return User.builder().emailId(userDto.getUserEmailId())
                .username(userDto.getUsername())
                .password(userDto.getUserEmailPassword())
                .build();
    }
}
