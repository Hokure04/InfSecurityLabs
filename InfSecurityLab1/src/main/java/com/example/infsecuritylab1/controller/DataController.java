package com.example.infsecuritylab1.controller;

import com.example.infsecuritylab1.model.User;
import com.example.infsecuritylab1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DataController {
    private final UserService userService;

    @GetMapping("/data")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
