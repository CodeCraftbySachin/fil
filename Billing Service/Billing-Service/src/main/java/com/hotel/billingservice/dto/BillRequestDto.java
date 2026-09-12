package com.hotel.billingservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class BillRequestDto {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    @PositiveOrZero(message = "Tax must be positive or zero")
    private Double tax = 0.0;

    public BillRequestDto() {
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }
}
