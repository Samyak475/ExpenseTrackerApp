package com.codeartist.authservice.services;

import com.codeartist.authservice.dtos.LoginRequestDto;
import com.codeartist.authservice.dtos.LoginResponseDto;

import com.codeartist.authservice.dtos.UserCreatedEvntDto;
import com.codeartist.authservice.dtos.UserDto;
import com.codeartist.authservice.entities.Tokens;
import com.codeartist.authservice.entities.User;
import com.codeartist.authservice.exceptions.UserAlreadyExistException;
import com.codeartist.authservice.producer.UserKafkaListener;
import com.codeartist.authservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    JWTUtilService jwtUtilService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserKafkaListener userKafkaListener;


    @Transactional
    public void signUpRequest(UserDto userDto){
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User newUser = getUserFromUserDto(userDto);
        System.out.println("here for signup");

//        String accessToken = jwtUtilService.generateToken(userDto);
//        String refreshToken = jwtUtilService.generateRefreshToken();
//        LocalDateTime expirationTime = LocalDateTime.now().plusMonths(1);
//        Tokens refreshTokenInDb = Tokens.builder().tokenId(refreshToken).expirationTime(expirationTime).build();
        try{
//            saveTokenInDb(refreshTokenInDb);
//            tokenRepo.save(refreshTokenInDb);
//            newUser.setTokens(refreshTokenInDb);
//            saveOrUpdateUserInDB(newUser);
        userRepo.saveAndFlush(newUser); // here we have used flush because spring jpa save the insert in jpa persistence
            // and only send all inserts if try block is success. But since SQL is not run, so catch block is missed due to which
            // userAlreadyExistException is not thrown. Part of using Transactional keyword.
            UserCreatedEvntDto userCreatedEvntDto = UserCreatedEvntDto.builder()
                    .email(newUser.getEmailId()).username(newUser.getUsername()).userId(newUser.getUserId()).build();
            userKafkaListener.sendToProducer(userCreatedEvntDto);
        }catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("Duplicate record found"+e.getMessage());
        }
        catch (Exception dt){
            throw new RuntimeException("Unable to access userData"+dt.getLocalizedMessage());
        }

//        return SignUpResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }



    @Transactional
    public LoginResponseDto loginRequest(LoginRequestDto requestDto){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(),requestDto.getPassword()));
        if(!authentication.isAuthenticated()){
            throw  new RuntimeException("User not authenticated");
        }

        User existingUser = (User) authentication.getPrincipal();
        UserDto userDto =  new UserDto().getUserDtoFromEntity(existingUser);
        userDto.setPassword(requestDto.getPassword());
        String accessToken = jwtUtilService.generateToken(userDto);
        String refreshToken = jwtUtilService.generateRefreshToken();
        LocalDateTime expirationTime = LocalDateTime.now().plusMonths(1);
        Tokens refreshTokenInDb = Tokens.builder().tokenId(refreshToken).expirationTime(expirationTime).build();
        try{
//            saveTokenInDb(refreshTokenInDb);
           // tokenRepo.save(refreshTokenInDb);
            existingUser.setTokens(refreshTokenInDb);
//            saveOrUpdateUserInDB(existingUser);
            userRepo.saveAndFlush(existingUser);
        } catch ( DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("Duplicate record found"+e.getMessage());
        }
        catch (Exception dt){
            throw new RuntimeException("Unable to access userData"+dt.getLocalizedMessage());
        }

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .email(userDto.getEmailId())
                .loginStatus("User Logged In Successfully")
                .username(userDto.getUsername())
                .refreshToken(refreshToken)
                .build();
    }


    public String generateAccessToken(String refreshToken){
//        Tokens tokens = Tokens.builder().tokenId(refreshToken).build();
        // this token will get full user object as JPA/Hibernate run the query on foriegn key which is TokenId and ignores rest of the fiedl
        // another way not create a full token object and still get existing user is below
Optional<User> existingUser = userRepo.getUserByTokens_TokenId(refreshToken);
//     Optional<User>existingUser = userRepo.getUserByTokens(tokens);
//     Tokens tokens = tokenRepo.getTokensByTokenId(refreshToken);
     if(existingUser.isEmpty()){
         throw new UsernameNotFoundException("Invalid Refresh Token");
     }

     if(LocalDateTime.now().isAfter(existingUser.get().getTokens().getExpirationTime())){
         throw new RuntimeException("Refresh Token expired, Pls Login Again");
     }

     UserDto existingUserDto = new UserDto().getUserDtoFromEntity(existingUser.get());
     return jwtUtilService.generateToken(existingUserDto);
    }







    public User getUserFromUserDto(UserDto userDto){
        return User.builder().emailId(userDto.getEmailId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }
}
