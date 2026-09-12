package com.hotel.bookingservice.service;

import com.hotel.bookingservice.dto.BookingRequestDto;
import com.hotel.bookingservice.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {

    BookingResponseDto createBooking(BookingRequestDto requestDto, Long userId);

    BookingResponseDto getBookingById(Long id);

    List<BookingResponseDto> getAllBookings();

    List<BookingResponseDto> getBookingsByUserId(Long userId);

    BookingResponseDto cancelBooking(Long id);
}
