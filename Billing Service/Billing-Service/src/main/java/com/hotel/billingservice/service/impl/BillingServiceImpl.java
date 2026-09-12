package com.hotel.billingservice.service.impl;

import com.hotel.billingservice.dto.BillRequestDto;
import com.hotel.billingservice.dto.BillResponseDto;
import com.hotel.billingservice.dto.BookingResponseDto;
import com.hotel.billingservice.entity.Bill;
import com.hotel.billingservice.entity.PaymentStatus;
import com.hotel.billingservice.exception.BillNotFoundException;
import com.hotel.billingservice.exception.InvalidBillException;
import com.hotel.billingservice.feign.BookingFeignClient;
import com.hotel.billingservice.repository.BillRepository;
import com.hotel.billingservice.service.BillingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingServiceImpl implements BillingService {

    private final BillRepository billRepository;
    private final BookingFeignClient bookingFeignClient;

    public BillingServiceImpl(BillRepository billRepository, BookingFeignClient bookingFeignClient) {
        this.billRepository = billRepository;
        this.bookingFeignClient = bookingFeignClient;
    }

    @Override
    @Transactional
    public BillResponseDto generateBill(BillRequestDto requestDto, String token) {

        if (billRepository.existsByBookingId(requestDto.getBookingId())) {
            throw new InvalidBillException("Bill already generated for booking id: " + requestDto.getBookingId());
        }

        BookingResponseDto booking;
        try {
            booking = bookingFeignClient.getBookingById(requestDto.getBookingId(), token);
        } catch (Exception ex) {
            throw new BillNotFoundException("Booking not found with id: " + requestDto.getBookingId());
        }

        if (booking == null) {
            throw new BillNotFoundException("Booking not found with id: " + requestDto.getBookingId());
        }

        double roomCharges = booking.getTotalPrice() != null ? booking.getTotalPrice() : 0.0;
        double tax = requestDto.getTax() != null ? requestDto.getTax() : 0.0;
        double totalAmount = roomCharges + tax;

        Bill bill = new Bill();
        bill.setBookingId(booking.getId());
        bill.setUserId(booking.getUserId());
        bill.setRoomCharges(roomCharges);
        bill.setTax(tax);
        bill.setTotalAmount(totalAmount);
        bill.setBillingDate(LocalDate.now());
        bill.setPaymentStatus(PaymentStatus.PENDING);

        Bill savedBill = billRepository.save(bill);
        return mapToResponse(savedBill);
    }

    @Override
    public BillResponseDto getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("Bill not found with id: " + id));
        return mapToResponse(bill);
    }

    @Override
    public List<BillResponseDto> getAllBills() {
        return billRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillResponseDto> getBillsByUserId(Long userId) {
        return billRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BillResponseDto payBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("Bill not found with id: " + id));

        bill.setPaymentStatus(PaymentStatus.PAID);
        Bill updatedBill = billRepository.save(bill);
        return mapToResponse(updatedBill);
    }

    private BillResponseDto mapToResponse(Bill bill) {
        BillResponseDto dto = new BillResponseDto();
        dto.setId(bill.getId());
        dto.setBookingId(bill.getBookingId());
        dto.setUserId(bill.getUserId());
        dto.setRoomCharges(bill.getRoomCharges());
        dto.setTax(bill.getTax());
        dto.setTotalAmount(bill.getTotalAmount());
        dto.setBillingDate(bill.getBillingDate());
        dto.setPaymentStatus(bill.getPaymentStatus().name());
        return dto;
    }
}
