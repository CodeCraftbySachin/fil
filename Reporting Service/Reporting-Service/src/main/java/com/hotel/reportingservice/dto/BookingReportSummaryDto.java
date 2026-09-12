package com.hotel.reportingservice.dto;

public class BookingReportSummaryDto {

    private long totalBookings;
    private long confirmedBookings;
    private long cancelledBookings;
    private double totalRevenueFromBookings;

    public BookingReportSummaryDto() {
    }

    public BookingReportSummaryDto(long totalBookings, long confirmedBookings, long cancelledBookings, double totalRevenueFromBookings) {
        this.totalBookings = totalBookings;
        this.confirmedBookings = confirmedBookings;
        this.cancelledBookings = cancelledBookings;
        this.totalRevenueFromBookings = totalRevenueFromBookings;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getConfirmedBookings() {
        return confirmedBookings;
    }

    public void setConfirmedBookings(long confirmedBookings) {
        this.confirmedBookings = confirmedBookings;
    }

    public long getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(long cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public double getTotalRevenueFromBookings() {
        return totalRevenueFromBookings;
    }

    public void setTotalRevenueFromBookings(double totalRevenueFromBookings) {
        this.totalRevenueFromBookings = totalRevenueFromBookings;
    }
}
