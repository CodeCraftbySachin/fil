package com.hotel.reportingservice.feign;

import com.hotel.reportingservice.dto.BillResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "BILLING-SERVICE")
public interface BillingFeignClient {

    @GetMapping("/billing")
    List<BillResponseDto> getAllBills(@RequestHeader(value = "Authorization", required = false) String token);
}
