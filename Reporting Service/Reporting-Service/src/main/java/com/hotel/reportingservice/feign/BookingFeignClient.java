package com.hotel.reportingservice.feign;

import com.hotel.reportingservice.dto.BookingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "BOOKING-SERVICE")
public interface BookingFeignClient {

    @GetMapping("/bookings")
    List<BookingResponseDto> getAllBookings(@RequestHeader(value = "Authorization", required = false) String token);
}
