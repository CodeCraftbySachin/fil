package com.hotel.billingservice.controller;

import com.hotel.billingservice.dto.BillRequestDto;
import com.hotel.billingservice.dto.BillResponseDto;
import com.hotel.billingservice.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<BillResponseDto> generateBill(
            @Valid @RequestBody BillRequestDto requestDto,
            @RequestHeader(value = "Authorization", required = false) String token) {

        BillResponseDto bill = billingService.generateBill(requestDto, token);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'GUEST')")
    public ResponseEntity<BillResponseDto> getBillById(@PathVariable Long id) {
        BillResponseDto bill = billingService.getBillById(id);
        return ResponseEntity.ok(bill);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<List<BillResponseDto>> getAllBills() {
        List<BillResponseDto> bills = billingService.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'GUEST')")
    public ResponseEntity<List<BillResponseDto>> getBillsByUserId(@PathVariable Long userId) {
        List<BillResponseDto> bills = billingService.getBillsByUserId(userId);
        return ResponseEntity.ok(bills);
    }

    @PutMapping("/{id}/pay")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'GUEST')")
    public ResponseEntity<BillResponseDto> payBill(@PathVariable Long id) {
        BillResponseDto paidBill = billingService.payBill(id);
        return ResponseEntity.ok(paidBill);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Billing Service is running!");
    }
}
