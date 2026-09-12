package com.hotel.billingservice.feign;

import com.hotel.billingservice.dto.BookingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "BOOKING-SERVICE")
public interface BookingFeignClient {

    @GetMapping("/bookings/{id}")
    BookingResponseDto getBookingById(
            @PathVariable("id") Long id,
            @RequestHeader(value = "Authorization", required = false) String token
    );
}
