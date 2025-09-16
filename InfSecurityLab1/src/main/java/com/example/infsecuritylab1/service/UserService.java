package com.example.infsecuritylab1.service;

import com.example.infsecuritylab1.models.User;
import com.example.infsecuritylab1.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User addUser(final User user){
//        user.setPassword(user.getPassword());
        User newUser = userRepository.save(user);
        log.info("{} registered successfully", user.getEmail());
        return newUser;
    }

    public User updateUser(final User user){
        User updUser = userRepository.save(user);
        log.info("{} updated successfully", user.getEmail());
        return updUser;
    }

    public boolean isExist(final String email){
        Optional<User> userForCheck = userRepository.findByEmail(email);
        if(userForCheck.isPresent()){
            log.info("User with email: {} already exist", email);
            return true;
        }
        log.info("User with email: {} not exist", email);
        return false;
    }

    public User getUserByEmail(final String email){
        if(!isExist(email)){
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }
        return userRepository.findByEmail(email).get();
    }

    public UserDetailsService getUserDetailsService() {
        return this::getUserByEmail;
    }
}
