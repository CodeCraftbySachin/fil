package com.hotel.bookingservice.feign;

import com.hotel.bookingservice.dto.RoomResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ROOM-SERVICE")
public interface RoomFeignClient {

    @GetMapping("/rooms/{id}")
    RoomResponseDto getRoomById(@PathVariable("id") Long id);
}
