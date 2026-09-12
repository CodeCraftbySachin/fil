package com.hotel.bookingservice.repository;

import com.hotel.bookingservice.entity.Booking;
import com.hotel.bookingservice.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByRoomId(Long roomId);

    boolean existsByRoomIdAndStatusAndCheckInBeforeAndCheckOutAfter(
            Long roomId,
            BookingStatus status,
            LocalDate checkOut,
            LocalDate checkIn
    );
}
