package com.springboot.services.userservice;

import com.springboot.dto.auth.UserLoginRequestDto;
import com.springboot.dto.auth.UserLoginResponseDto;
import com.springboot.dto.auth.UserSignupRequestDto;
import com.springboot.dto.auth.UserSignupResponseDto;

public interface UserService {
    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);
    UserSignupResponseDto signup(UserSignupRequestDto userSignupRequestDto);
}
