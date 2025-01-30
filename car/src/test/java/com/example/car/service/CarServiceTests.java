package com.example.car;

import com.example.car.model.Car;
import com.example.car.model.User;
import com.example.car.repository.CARREPO;
import com.example.car.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CarServiceTests {

    @Mock
    private CARREPO carRepository;
    @InjectMocks
    private CarService carService;


    @Test
    public void saveCarTest(){
        Car car=new Car();
        car.setId(1L);
        car.setCarId(1);
        car.setURL("URL");
        car.setModel("dinesh");
        car.setPrice(12000);
        car.setUser(null);

        Mockito.when(carService.save(car)).thenReturn(car);

        Car savedCar=carService.save(car);

        assertNotNull(savedCar);
        assertEquals(savedCar.getModel(),"dinesh");
    }

    @Test
    public void deleteCarTest(){
        Long id=1L;

        carService.delete(id);
        Mockito.verify(carRepository,Mockito.times(1)).deleteById(id);
    }


}