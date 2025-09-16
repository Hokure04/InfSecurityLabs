package com.example.infsecuritylab1.authorization;

import com.example.infsecuritylab1.service.AuthService;
import com.example.infsecuritylab1.dto.ApplicationResponseDto;
import com.example.infsecuritylab1.dto.LoginDto;
import com.example.infsecuritylab1.dto.RegistrationDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ApplicationResponseDto signUp(@RequestBody RegistrationDto request){
        return authService.signUp(request);
    }

    @PostMapping("/login")
    public ApplicationResponseDto login(@RequestBody LoginDto request){
        return authService.login(request);
    }
}
