package com.hotel.billingservice.repository;

import com.hotel.billingservice.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByUserId(Long userId);

    Optional<Bill> findByBookingId(Long bookingId);

    boolean existsByBookingId(Long bookingId);
}
