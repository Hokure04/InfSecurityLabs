package com.example.infsecuritylab1.service;

import com.example.infsecuritylab1.dto.ApplicationResponseDto;
import com.example.infsecuritylab1.dto.LoginDto;
import com.example.infsecuritylab1.dto.RegistrationDto;
import com.example.infsecuritylab1.exception.AuthorizeException;
import com.example.infsecuritylab1.exception.FieldNotSpecifiedException;
import com.example.infsecuritylab1.models.Role;
import com.example.infsecuritylab1.models.User;
import com.example.infsecuritylab1.services.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public ApplicationResponseDto signUp(RegistrationDto request){
        var userBuilder = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(Role.SIMPLE_USER)
                .password(passwordEncoder.encode(request.getPassword()));
        log.info(userBuilder.build().getPassword());
        var user = userBuilder.build();

        if(userService.isExist(user.getEmail())){
            log.warn("User with username: {} exist", user.getEmail());
            throw new AuthorizeException("Пользователь с email " + user.getEmail() + " уже существует");
        }
        userService.addUser(user);

        var jwt = jwtService.generateToken(user);
        return new ApplicationResponseDto(jwt);

    }

    public ApplicationResponseDto login(LoginDto request){
        if(request.getEmail() == null || request.getEmail().isEmpty()){
            throw new FieldNotSpecifiedException("Поле email обязательно");
        }
        if(request.getPassword() == null || request.getPassword().isEmpty()){
            throw new FieldNotSpecifiedException("Поле password обязательно");
        }

        User user;
        try{
            user = userService.getUserByEmail(request.getEmail());
            log.debug("Stored hash: {}", user.getPassword());

            if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
                throw new AuthorizeException("пароль указан неверно");
            }
        }catch (UsernameNotFoundException e){
            throw new AuthorizeException("Пользователя с данным email не существует");
        }
        var jwt = jwtService.generateToken(user);
        return new ApplicationResponseDto(jwt);
    }

    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            return userService.getUserByEmail(username);
        }else{
            throw new AuthorizeException("Текущий пользователь не авторизовн");
        }
    }
}
