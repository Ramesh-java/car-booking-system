package com.example.car.service;

import com.example.car.model.Car;
import com.example.car.model.Cars;
import com.example.car.model.User;
import com.example.car.repository.CARREPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CARREPO carrepo;

    public Car save(Car car){
        return carrepo.save(car);
    }

    public List<Car> cars(User user){
        return carrepo.findByUser(user);
    }

    public void delete(Long id){
        carrepo.deleteById(id);
    }

    public Cars getCarById(int id,List<Cars>list){
        for (Cars c:list){
            if (id==c.getId()){
                return c;
            }
        }return null;
    }
}