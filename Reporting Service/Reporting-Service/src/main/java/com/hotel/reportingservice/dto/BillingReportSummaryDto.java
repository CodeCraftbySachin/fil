package com.hotel.reportingservice.dto;

public class BillingReportSummaryDto {

    private long totalBills;
    private long paidBillsCount;
    private long pendingBillsCount;
    private double totalBilledAmount;
    private double totalPaidAmount;
    private double totalPendingAmount;

    public BillingReportSummaryDto() {
    }

    public BillingReportSummaryDto(long totalBills, long paidBillsCount, long pendingBillsCount, double totalBilledAmount, double totalPaidAmount, double totalPendingAmount) {
        this.totalBills = totalBills;
        this.paidBillsCount = paidBillsCount;
        this.pendingBillsCount = pendingBillsCount;
        this.totalBilledAmount = totalBilledAmount;
        this.totalPaidAmount = totalPaidAmount;
        this.totalPendingAmount = totalPendingAmount;
    }

    public long getTotalBills() {
        return totalBills;
    }

    public void setTotalBills(long totalBills) {
        this.totalBills = totalBills;
    }

    public long getPaidBillsCount() {
        return paidBillsCount;
    }

    public void setPaidBillsCount(long paidBillsCount) {
        this.paidBillsCount = paidBillsCount;
    }

    public long getPendingBillsCount() {
        return pendingBillsCount;
    }

    public void setPendingBillsCount(long pendingBillsCount) {
        this.pendingBillsCount = pendingBillsCount;
    }

    public double getTotalBilledAmount() {
        return totalBilledAmount;
    }

    public void setTotalBilledAmount(double totalBilledAmount) {
        this.totalBilledAmount = totalBilledAmount;
    }

    public double getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(double totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public double getTotalPendingAmount() {
        return totalPendingAmount;
    }

    public void setTotalPendingAmount(double totalPendingAmount) {
        this.totalPendingAmount = totalPendingAmount;
    }
}
