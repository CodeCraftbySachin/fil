package com.hotel.billingservice.exception;

public class InvalidBillException extends RuntimeException {
    public InvalidBillException(String message) {
        super(message);
    }
}
