package com.example.booking_service.controller;

import com.example.booking_service.dto.BookingResponseDTO;
import com.example.booking_service.client.CustomerClient;
import com.example.booking_service.client.VehicleClient;
import com.example.booking_service.dto.CustomerDTO;
import com.example.booking_service.dto.VehicleDTO;
import com.example.booking_service.entity.Booking;
import com.example.booking_service.repository.BookingRepository;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final CustomerClient customerClient;
    private final VehicleClient vehicleClient;

    public BookingController(BookingRepository bookingRepository, CustomerClient customerClient, VehicleClient vehicleClient) {
        this.bookingRepository = bookingRepository;
        this.customerClient = customerClient;
        this.vehicleClient = vehicleClient;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            CustomerDTO customer = customerClient.getCustomerById(booking.getCustomerId());
            VehicleDTO vehicle = vehicleClient.getVehicleById(booking.getVehicleId());

            if (customer != null && vehicle != null) {
                Booking savedBooking = bookingRepository.save(booking);

                BookingResponseDTO fullResponse = new BookingResponseDTO(
                    savedBooking.getId(),
                    savedBooking.getBookingDate(),
                    savedBooking.getAmount(),
                    customer,
                    vehicle
                );

                return ResponseEntity.ok(fullResponse);
            }
            
            return ResponseEntity.badRequest().body("Customer or Vehicle not found!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error connecting to services: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        
        List<BookingResponseDTO> response = bookings.stream().map(booking -> {
            CustomerDTO customer = null;
            VehicleDTO vehicle = null;
            
            try {
                customer = customerClient.getCustomerById(booking.getCustomerId());
                vehicle = vehicleClient.getVehicleById(booking.getVehicleId());
            } catch (Exception e) {
                System.err.println("Error fetching details for booking " + booking.getId());
            }

            return new BookingResponseDTO(
                booking.getId(),
                booking.getBookingDate(),
                booking.getAmount(),
                customer,
                vehicle
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable String id) {
        return bookingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable String id) {
        if (!bookingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookingRepository.deleteById(id);
        return ResponseEntity.ok("Booking deleted successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable String id, @RequestBody Booking bookingUpdates) {
        return bookingRepository.findById(id)
                .map(existingBooking -> {
                    existingBooking.setBookingDate(bookingUpdates.getBookingDate());
                    existingBooking.setAmount(bookingUpdates.getAmount());
                    
                    Booking updatedBooking = bookingRepository.save(existingBooking);
                    return ResponseEntity.ok(updatedBooking);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}