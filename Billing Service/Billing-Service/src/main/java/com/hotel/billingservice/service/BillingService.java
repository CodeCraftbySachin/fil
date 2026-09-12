package com.hotel.billingservice.service;

import com.hotel.billingservice.dto.BillRequestDto;
import com.hotel.billingservice.dto.BillResponseDto;

import java.util.List;

public interface BillingService {

    BillResponseDto generateBill(BillRequestDto requestDto, String token);

    BillResponseDto getBillById(Long id);

    List<BillResponseDto> getAllBills();

    List<BillResponseDto> getBillsByUserId(Long userId);

    BillResponseDto payBill(Long id);
}
