package com.hotel.reportingservice.feign;

import com.hotel.reportingservice.dto.RoomResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "ROOM-SERVICE")
public interface RoomFeignClient {

    @GetMapping("/rooms")
    List<RoomResponseDto> getAllRooms(@RequestHeader(value = "Authorization", required = false) String token);
}
