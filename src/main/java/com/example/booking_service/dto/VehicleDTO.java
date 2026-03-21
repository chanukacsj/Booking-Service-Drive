package com.example.booking_service.dto;

public class VehicleDTO {
    private Long id;
    private String vehicleNumber;
    private String brand;
    private String model;
    private String fuelType;
    private String imageUrl; 

    // Default Constructor
    public VehicleDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "id=" + id +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", model='" + model + '\'' +
                ", imageUrl='" + imageUrl + '\'' + 
                '}';
    }
}