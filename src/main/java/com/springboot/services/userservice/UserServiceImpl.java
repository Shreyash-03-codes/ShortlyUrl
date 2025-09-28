package com.springboot.services.userservice;

import com.springboot.dto.auth.UserLoginRequestDto;
import com.springboot.dto.auth.UserLoginResponseDto;
import com.springboot.dto.auth.UserSignupRequestDto;
import com.springboot.dto.auth.UserSignupResponseDto;
import com.springboot.entity.User;
import com.springboot.enums.Roles;
import com.springboot.repositories.UserRepository;
import com.springboot.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword())
            );
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(user);
            User u = userRepository.findByEmail(user.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + user.getUsername()));
            return new UserLoginResponseDto(user.getUsername(), token, u.getRoles());
        } catch (UsernameNotFoundException ex) {
            throw ex; // let Spring Security handle this
        } catch (Exception ex) {
            throw new RuntimeException("Login failed: " + ex.getMessage());
        }
    }

    @Override
    public UserSignupResponseDto signup(UserSignupRequestDto userSignupRequestDto) {
        try {
            User user = new User(
                    Roles.USER,
                    passwordEncoder.encode(userSignupRequestDto.getPassword()),
                    userSignupRequestDto.getEmail(),
                    userSignupRequestDto.getName()
            );
            User resp = userRepository.save(user);
            return new UserSignupResponseDto(resp.getName(), resp.getEmail());
        } catch (Exception ex) {
            throw new RuntimeException("Signup failed: " + ex.getMessage());
        }
    }
}
