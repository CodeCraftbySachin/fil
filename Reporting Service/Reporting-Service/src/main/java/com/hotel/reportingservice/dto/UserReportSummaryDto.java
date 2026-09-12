package com.hotel.reportingservice.dto;

public class UserReportSummaryDto {

    private long totalUsers;
    private long adminCount;
    private long staffCount;
    private long guestCount;

    public UserReportSummaryDto() {
    }

    public UserReportSummaryDto(long totalUsers, long adminCount, long staffCount, long guestCount) {
        this.totalUsers = totalUsers;
        this.adminCount = adminCount;
        this.staffCount = staffCount;
        this.guestCount = guestCount;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(long adminCount) {
        this.adminCount = adminCount;
    }

    public long getStaffCount() {
        return staffCount;
    }

    public void setStaffCount(long staffCount) {
        this.staffCount = staffCount;
    }

    public long getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(long guestCount) {
        this.guestCount = guestCount;
    }
}
