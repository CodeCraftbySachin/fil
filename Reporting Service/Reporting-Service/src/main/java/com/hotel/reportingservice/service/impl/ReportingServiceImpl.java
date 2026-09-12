package com.hotel.reportingservice.service.impl;

import com.hotel.reportingservice.dto.*;
import com.hotel.reportingservice.feign.BillingFeignClient;
import com.hotel.reportingservice.feign.BookingFeignClient;
import com.hotel.reportingservice.feign.RoomFeignClient;
import com.hotel.reportingservice.feign.UserFeignClient;
import com.hotel.reportingservice.service.ReportingService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final BookingFeignClient bookingFeignClient;
    private final BillingFeignClient billingFeignClient;
    private final RoomFeignClient roomFeignClient;
    private final UserFeignClient userFeignClient;

    public ReportingServiceImpl(
            BookingFeignClient bookingFeignClient,
            BillingFeignClient billingFeignClient,
            RoomFeignClient roomFeignClient,
            UserFeignClient userFeignClient) {
        this.bookingFeignClient = bookingFeignClient;
        this.billingFeignClient = billingFeignClient;
        this.roomFeignClient = roomFeignClient;
        this.userFeignClient = userFeignClient;
    }

    @Override
    public BookingReportSummaryDto getBookingSummary(String token) {
        List<BookingResponseDto> bookings;
        try {
            bookings = bookingFeignClient.getAllBookings(token);
        } catch (Exception ex) {
            bookings = Collections.emptyList();
        }

        long total = bookings.size();
        long confirmed = bookings.stream()
                .filter(b -> "CONFIRMED".equalsIgnoreCase(b.getStatus()))
                .count();
        long cancelled = bookings.stream()
                .filter(b -> "CANCELLED".equalsIgnoreCase(b.getStatus()))
                .count();
        double totalRevenue = bookings.stream()
                .filter(b -> "CONFIRMED".equalsIgnoreCase(b.getStatus()))
                .mapToDouble(b -> b.getTotalPrice() != null ? b.getTotalPrice() : 0.0)
                .sum();

        return new BookingReportSummaryDto(total, confirmed, cancelled, totalRevenue);
    }

    @Override
    public BillingReportSummaryDto getBillingSummary(String token) {
        List<BillResponseDto> bills;
        try {
            bills = billingFeignClient.getAllBills(token);
        } catch (Exception ex) {
            bills = Collections.emptyList();
        }

        long totalBills = bills.size();
        long paidCount = bills.stream()
                .filter(b -> "PAID".equalsIgnoreCase(b.getPaymentStatus()))
                .count();
        long pendingCount = bills.stream()
                .filter(b -> "PENDING".equalsIgnoreCase(b.getPaymentStatus()))
                .count();

        double totalBilled = bills.stream()
                .mapToDouble(b -> b.getTotalAmount() != null ? b.getTotalAmount() : 0.0)
                .sum();
        double totalPaid = bills.stream()
                .filter(b -> "PAID".equalsIgnoreCase(b.getPaymentStatus()))
                .mapToDouble(b -> b.getTotalAmount() != null ? b.getTotalAmount() : 0.0)
                .sum();
        double totalPending = bills.stream()
                .filter(b -> "PENDING".equalsIgnoreCase(b.getPaymentStatus()))
                .mapToDouble(b -> b.getTotalAmount() != null ? b.getTotalAmount() : 0.0)
                .sum();

        return new BillingReportSummaryDto(totalBills, paidCount, pendingCount, totalBilled, totalPaid, totalPending);
    }

    @Override
    public RoomReportSummaryDto getRoomSummary(String token) {
        List<RoomResponseDto> rooms;
        try {
            rooms = roomFeignClient.getAllRooms(token);
        } catch (Exception ex) {
            rooms = Collections.emptyList();
        }

        long totalRooms = rooms.size();
        long availableRooms = rooms.stream()
                .filter(r -> Boolean.TRUE.equals(r.getAvailable()))
                .count();
        long occupiedRooms = totalRooms - availableRooms;

        return new RoomReportSummaryDto(totalRooms, availableRooms, occupiedRooms);
    }

    @Override
    public UserReportSummaryDto getUserSummary(String token) {
        List<UserResponseDto> users;
        try {
            users = userFeignClient.getAllUsers(token);
        } catch (Exception ex) {
            users = Collections.emptyList();
        }

        long totalUsers = users.size();
        long adminCount = users.stream()
                .filter(u -> "ADMIN".equalsIgnoreCase(u.getRole()))
                .count();
        long staffCount = users.stream()
                .filter(u -> "STAFF".equalsIgnoreCase(u.getRole()))
                .count();
        long guestCount = users.stream()
                .filter(u -> "GUEST".equalsIgnoreCase(u.getRole()))
                .count();

        return new UserReportSummaryDto(totalUsers, adminCount, staffCount, guestCount);
    }
}
