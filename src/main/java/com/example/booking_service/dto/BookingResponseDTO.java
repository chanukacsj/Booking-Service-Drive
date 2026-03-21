package com.example.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private String id;
    private String bookingDate;
    private Double amount;
    private CustomerDTO customer; 
    private VehicleDTO vehicle;   
}