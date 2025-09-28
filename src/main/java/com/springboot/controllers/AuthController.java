package com.springboot.controllers;

import com.springboot.dto.auth.UserLoginRequestDto;
import com.springboot.dto.auth.UserLoginResponseDto;
import com.springboot.dto.auth.UserSignupRequestDto;
import com.springboot.dto.auth.UserSignupResponseDto;
import com.springboot.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/public/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {
            UserLoginResponseDto response = userService.login(userLoginRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + ex.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {
        try {
            UserSignupResponseDto response = userService.signup(userSignupRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + ex.getMessage());
        }
    }
}
