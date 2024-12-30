package com.example.car.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class Cars {
    private String model;
    private String URL;
    private double price;

    public Cars(String model, String URL, double price, int id) {
        this.model = model;
        this.URL = URL;
        this.price = price;
        this.id = id;
    }

    private int id;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
