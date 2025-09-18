package com.example.infsecuritylab1.service;

import com.example.infsecuritylab1.model.Role;
import com.example.infsecuritylab1.model.User;
import com.example.infsecuritylab1.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User addUser(final User user){
        User newUser = userRepository.save(user);
        log.info("{} registered successfully", user.getEmail());
        return newUser;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public long countUsers(){
        return userRepository.count();
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
    

    public User promoteToAdmin(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setRole(Role.ROLE_ADMIN);
        return userRepository.save(user);
    }

    public User blockUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(false);
        return userRepository.save(user);
    }

    public UserDetailsService getUserDetailsService() {
        return this::getUserByEmail;
    }
}
