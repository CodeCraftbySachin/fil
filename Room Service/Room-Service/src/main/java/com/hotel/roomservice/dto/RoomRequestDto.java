package com.hotel.roomservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RoomRequestDto {

	@NotBlank(message = "Room number is required")
    private String roomNumber;
	@NotBlank(message = "Room type is required")
    private String roomType;
	@NotNull(message = "Price is required")
	@Positive(message = "Price must be positive")
    private Double price;
	@NotNull(message = "Availability status is required")
    private Boolean available;

    public RoomRequestDto() {
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}