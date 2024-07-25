package com.demo.mwm.controller;

import com.demo.mwm.dto.UserDto;
import com.demo.mwm.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto){
        authService.register(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Created successfully");
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok()
                .body(authService.login(userDto));
    }

}
