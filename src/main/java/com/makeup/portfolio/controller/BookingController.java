package com.makeup.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makeup.portfolio.DTO.BookingRequest;
import com.makeup.portfolio.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        // servizio email
        bookingService.sendBookingEmail(
                request.getCustomerName(),
                request.getCustomerEmail(),
                request.getServiceTitle(),
                request.getDate());
        return ResponseEntity.ok().body("Prenotazione inviata!");
    }
}
