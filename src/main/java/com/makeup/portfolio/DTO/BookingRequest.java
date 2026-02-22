package com.makeup.portfolio.DTO;

import lombok.Data;

@Data
public class BookingRequest {
    private String customerName;
    private String customerEmail;
    private String serviceTitle;
    private String date; 
}
