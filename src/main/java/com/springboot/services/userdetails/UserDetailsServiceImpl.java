package com.springboot.services.userdetails;

import com.springboot.entity.User;
import com.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return user;
    }
}
