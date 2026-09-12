package com.hotel.authservice.service.impl;

import com.hotel.authservice.dto.AuthResponseDto;
import com.hotel.authservice.dto.LoginRequestDto;
import com.hotel.authservice.dto.RegisterRequestDto;
import com.hotel.authservice.dto.UserRequestDto;
import com.hotel.authservice.dto.UserResponseDto;
import com.hotel.authservice.dto.AuthUserResponseDto;
import com.hotel.authservice.exception.InvalidCredentialsException;
import com.hotel.authservice.feign.UserFeignClient;
import com.hotel.authservice.security.JwtUtil;
import com.hotel.authservice.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserFeignClient userFeignClient;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserFeignClient userFeignClient,
                           JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder) {

        this.userFeignClient = userFeignClient;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto register(RegisterRequestDto requestDto) {

        UserRequestDto userRequestDto = new UserRequestDto();

        userRequestDto.setName(requestDto.getName());
        userRequestDto.setEmail(requestDto.getEmail());
        userRequestDto.setPassword(
                passwordEncoder.encode(requestDto.getPassword()));
        userRequestDto.setRole(requestDto.getRole());

        return userFeignClient.createUser(userRequestDto);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto requestDto) {

        AuthUserResponseDto user =
                userFeignClient.getUserByEmail(requestDto.getEmail());

        if (!passwordEncoder.matches(
                requestDto.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password");
        }

        String token = jwtUtil.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole());

        return new AuthResponseDto(token);
    }
}