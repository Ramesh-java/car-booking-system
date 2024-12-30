package com.example.car.model;

import jakarta.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private double price;
    private int carId;

    @ManyToOne
    private User user;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }


    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private String URL;
    public void setId(Long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public User getUser() {
        return user;
    }
}
