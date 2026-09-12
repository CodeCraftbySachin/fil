package com.hotel.reportingservice.service;

import com.hotel.reportingservice.dto.BillingReportSummaryDto;
import com.hotel.reportingservice.dto.BookingReportSummaryDto;
import com.hotel.reportingservice.dto.RoomReportSummaryDto;
import com.hotel.reportingservice.dto.UserReportSummaryDto;

public interface ReportingService {

    BookingReportSummaryDto getBookingSummary(String token);

    BillingReportSummaryDto getBillingSummary(String token);

    RoomReportSummaryDto getRoomSummary(String token);

    UserReportSummaryDto getUserSummary(String token);
}
