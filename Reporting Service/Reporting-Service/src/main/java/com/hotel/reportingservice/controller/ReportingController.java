package com.hotel.reportingservice.controller;

import com.hotel.reportingservice.dto.BillingReportSummaryDto;
import com.hotel.reportingservice.dto.BookingReportSummaryDto;
import com.hotel.reportingservice.dto.RoomReportSummaryDto;
import com.hotel.reportingservice.dto.UserReportSummaryDto;
import com.hotel.reportingservice.service.ReportingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/bookings/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<BookingReportSummaryDto> getBookingSummary(
            @RequestHeader(value = "Authorization", required = false) String token) {
        BookingReportSummaryDto summary = reportingService.getBookingSummary(token);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/billing/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<BillingReportSummaryDto> getBillingSummary(
            @RequestHeader(value = "Authorization", required = false) String token) {
        BillingReportSummaryDto summary = reportingService.getBillingSummary(token);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/rooms/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<RoomReportSummaryDto> getRoomSummary(
            @RequestHeader(value = "Authorization", required = false) String token) {
        RoomReportSummaryDto summary = reportingService.getRoomSummary(token);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/users/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<UserReportSummaryDto> getUserSummary(
            @RequestHeader(value = "Authorization", required = false) String token) {
        UserReportSummaryDto summary = reportingService.getUserSummary(token);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Reporting Service is running!");
    }
}
