package com.example.booking_service.client;

import com.example.booking_service.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service") 
public interface CustomerClient {

    @GetMapping("/api/v1/customers/{id}") 
    CustomerDTO getCustomerById(@PathVariable("id") Long id);
}