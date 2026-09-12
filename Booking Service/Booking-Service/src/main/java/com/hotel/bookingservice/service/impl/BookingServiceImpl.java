package com.hotel.bookingservice.service.impl;

import com.hotel.bookingservice.dto.BookingRequestDto;
import com.hotel.bookingservice.dto.BookingResponseDto;
import com.hotel.bookingservice.dto.RoomResponseDto;
import com.hotel.bookingservice.entity.Booking;
import com.hotel.bookingservice.entity.BookingStatus;
import com.hotel.bookingservice.exception.BookingNotFoundException;
import com.hotel.bookingservice.exception.InvalidBookingException;
import com.hotel.bookingservice.exception.RoomNotAvailableException;
import com.hotel.bookingservice.feign.RoomFeignClient;
import com.hotel.bookingservice.repository.BookingRepository;
import com.hotel.bookingservice.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomFeignClient roomFeignClient;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomFeignClient roomFeignClient) {
        this.bookingRepository = bookingRepository;
        this.roomFeignClient = roomFeignClient;
    }

    @Override
    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto requestDto, Long userId) {

        // 1. Validate dates: Check-in date comes before Check-out date
        if (!requestDto.getCheckOut().isAfter(requestDto.getCheckIn())) {
            throw new InvalidBookingException("Check-out date must be after check-in date");
        }

        // 2. Fetch Room details via Feign client
        RoomResponseDto room;
        try {
            room = roomFeignClient.getRoomById(requestDto.getRoomId());
        } catch (Exception ex) {
            throw new RoomNotAvailableException("Room not found with id: " + requestDto.getRoomId());
        }

        if (room == null || !Boolean.TRUE.equals(room.getAvailable())) {
            throw new RoomNotAvailableException("Room is not available for booking");
        }

        // 3. Check for double booking / overlapping dates for the same room
        boolean isBooked = bookingRepository.existsByRoomIdAndStatusAndCheckInBeforeAndCheckOutAfter(
                requestDto.getRoomId(),
                BookingStatus.CONFIRMED,
                requestDto.getCheckOut(),
                requestDto.getCheckIn()
        );

        if (isBooked) {
            throw new RoomNotAvailableException("Room is already booked for the selected dates");
        }

        // 4. Calculate total price
        long days = ChronoUnit.DAYS.between(requestDto.getCheckIn(), requestDto.getCheckOut());
        if (days <= 0) {
            days = 1;
        }
        double totalPrice = days * room.getPrice();

        // 5. Create and save booking entity
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setRoomId(requestDto.getRoomId());
        booking.setCheckIn(requestDto.getCheckIn());
        booking.setCheckOut(requestDto.getCheckOut());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setTotalPrice(totalPrice);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }

    @Override
    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        return mapToResponse(booking);
    }

    @Override
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponseDto cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));

        booking.setStatus(BookingStatus.CANCELLED);
        Booking updatedBooking = bookingRepository.save(booking);
        return mapToResponse(updatedBooking);
    }

    private BookingResponseDto mapToResponse(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUserId());
        dto.setRoomId(booking.getRoomId());
        dto.setCheckIn(booking.getCheckIn());
        dto.setCheckOut(booking.getCheckOut());
        dto.setStatus(booking.getStatus().name());
        dto.setTotalPrice(booking.getTotalPrice());
        return dto;
    }
}
