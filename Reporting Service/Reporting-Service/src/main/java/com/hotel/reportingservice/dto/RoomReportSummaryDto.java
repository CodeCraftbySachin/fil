package com.hotel.reportingservice.dto;

public class RoomReportSummaryDto {

    private long totalRooms;
    private long availableRooms;
    private long occupiedRooms;

    public RoomReportSummaryDto() {
    }

    public RoomReportSummaryDto(long totalRooms, long availableRooms, long occupiedRooms) {
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.occupiedRooms = occupiedRooms;
    }

    public long getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(long totalRooms) {
        this.totalRooms = totalRooms;
    }

    public long getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(long availableRooms) {
        this.availableRooms = availableRooms;
    }

    public long getOccupiedRooms() {
        return occupiedRooms;
    }

    public void setOccupiedRooms(long occupiedRooms) {
        this.occupiedRooms = occupiedRooms;
    }
}
