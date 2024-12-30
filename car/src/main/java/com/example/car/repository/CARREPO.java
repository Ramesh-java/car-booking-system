package com.example.car.repository;

import com.example.car.model.Car;
import com.example.car.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CARREPO extends JpaRepository<Car,Long> {
    public List<Car>findByUser(User user);
}
