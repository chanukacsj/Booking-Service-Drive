package com.example.booking_service.client;

import com.example.booking_service.dto.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehicle-service") // Eureka Dashboard එකේ තියෙන නමම වෙන්න ඕනේ
public interface VehicleClient {

    @GetMapping("/api/v1/vehicles/{id}") // Vehicle Service එකේ අදාළ Endpoint එක
    VehicleDTO getVehicleById(@PathVariable("id") Long id);
}