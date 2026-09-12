package com.hotel.reportingservice.feign;

import com.hotel.reportingservice.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "USER-SERVICE")
public interface UserFeignClient {

    @GetMapping("/users")
    List<UserResponseDto> getAllUsers(@RequestHeader(value = "Authorization", required = false) String token);
}
