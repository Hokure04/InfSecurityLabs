package com.example.infsecuritylab1.controller;

import com.example.infsecuritylab1.model.User;
import com.example.infsecuritylab1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/promote/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User promoteUser(@PathVariable Long id){
        return userService.promoteToAdmin(id);
    }

    @PostMapping("/block/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User blockUser(@PathVariable Long id){
        return userService.blockUser(id);
    }
}
