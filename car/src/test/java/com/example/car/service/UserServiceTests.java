package org.example.java.carrr;

import org.example.java.carrr.Entity.Car;
import org.example.java.carrr.Entity.User;
import org.example.java.carrr.Repositary.CarRepo;
import org.example.java.carrr.Repositary.UserRepo;
import org.example.java.carrr.Service.CarService;
import org.example.java.carrr.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CarrrApplicationTests {

    @Test
    void contextLoads() {
    }

    //User Testing

    @Mock
    UserRepo userRepo;

    @InjectMocks
    UserService service;

    @Test
    void testCreateUser(){
        User user = new User();
        user.setEmail("sample123@gmail.com");
        user.setUserName("xyz");
        user.setPassword("123");
        service.create(user);
        verify(userRepo).save(user);
    }

    @Test
    void testGetUserByEmail(){
        User user = new User();
        user.setEmail("sample123@gmail.com");
        user.setUserName("xyz");
        user.setPassword("123");
        when(userRepo.getUserByEmail(user.getEmail())).thenReturn(user);
        User resultUser = service.getUser(user.getEmail());
        assertNotNull(resultUser);
        assertEquals(user.getEmail(), resultUser.getEmail());
        assertEquals(user.getUserName(), resultUser.getUserName());
        assertEquals(user.getPassword(), resultUser.getPassword());
        verify(userRepo).getUserByEmail(user.getEmail());
    }


    //Product testing

    @Mock
    CarRepo carRepo;

    @InjectMocks
    CarService carService;

    @Test
    void testSaveCars(){
        Car car = new Car();
        car.setId(1L);
        car.setCarId(1);
        car.setImageAddress("Address");
        car.setModel("Benz");
        car.setPrice(5000000);
        car.setUser(null);
        Mockito.when(carService.save(car)).thenReturn(car);
        Car newCar = carService.save(car);
        assertNotNull(newCar);
        assertEquals(newCar.getModel(), "Benz");
        verify(carRepo,times(1)).save(car);
    }

    @Test
    void testDeleteById(){
        Long carId = 1L;
        carService.delete(carId);
        Mockito.verify(carRepo,times(1)).deleteById(carId);
    }

}
