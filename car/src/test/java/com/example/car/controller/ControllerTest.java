package com.example.car.controller;


import com.example.car.model.Car;
import com.example.car.model.Cars;
import com.example.car.model.User;
import com.example.car.service.CarService;
import com.example.car.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    @InjectMocks
    private Controller controller;

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private CarService carService;

    User currentUser;
    List<Car>cars=new ArrayList<>();
    @BeforeEach
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();

        currentUser=new User();
        currentUser.setId(1L);
        currentUser.setName("dummy");

        Car car=new Car();
        car.setPrice(1000);
        car.setURL("");
        car.setModel("BMW");
        car.setUser(currentUser);
        car.setCarId(1);

        cars.add(car);
    }

    @Test
    public void signUpTest() throws Exception{
        String name="exampleName";
        String number="1234567890";
        String password="*****";

        mockMvc.perform(post("/Signed")
                .param("name",name)
                .param("mobile",number)
                .param("password",password))
                .andExpect(status().isOk())
                .andExpect(view().name("LOGIN"));
        Mockito.verify(userService).save(any(User.class));
    }
    @Test
    public void loginTest() throws Exception{
        String number="1234567890";
        String password="*****";

        User mockuser=new User();
        mockuser.setMobile(number);
        mockuser.setPassword(password);

        Mockito.when(userService.getUser(number)).thenReturn(mockuser);

        mockMvc.perform(post("/logged")
                .param("mobile",number)
                .param("password",password))
                .andExpect(status().isOk())
                .andExpect(view().name("DASHBOARD"));

        Mockito.verify(userService).getUser(number);

    }

    @Test
    public void addToCartWhenFound() throws Exception{
        int carId=1;
        Cars car=new Cars("BMW","url",100,1);
        List<Cars>list=new ArrayList<>();
        ReflectionTestUtils.setField(controller,"list",list);
        Mockito.when(carService.getCarById(carId,list)).thenReturn(car);

        mockMvc.perform(get("/add/{carid}",carId)).andExpect(status().isOk()).andExpect(view().name("DASHBOARD"));


        Mockito.verify(carService).save(any(Car.class));
    }

    /*@Test
    public void addToCartWhenNotFound() throws Exception{
        int carId=1;
        Mockito.when(carService.getCarById(carId,null)).thenReturn(null);
        mockMvc.perform(get("/add/{carid}",carId)).andExpect(status().isOk()).andExpect(view().name("DASHBOARD"));
        Mockito.verify(carService,Mockito.never()).save(any(Car.class));
    }*/
    @Test
    public void viewCartTest() throws Exception{
        User mockUser=new User();
        List<Car>cars=new ArrayList<>();
        Car car=new Car();
        car.setPrice(1000);
        car.setURL("");
        car.setModel("BMW");
        car.setUser(mockUser);
        car.setCarId(1);
        car.setId(1L);
        cars.add(car);
        ReflectionTestUtils.setField(controller, "currentUser", mockUser);
        Mockito.when(carService.cars(mockUser)).thenReturn(cars);
        mockMvc.perform(get("/cart")).andExpect(status().isOk()).andExpect(view().name("CART"));
        Mockito.verify(carService).cars(mockUser);
    }

    @Test
    public void deleteFromCartTest() throws Exception{

        Long id=1L;

        User mockUser=new User();
        List<Car>cars=new ArrayList<>();
        Car car=new Car();
        car.setPrice(1000);
        car.setURL("");
        car.setModel("BMW");
        car.setUser(mockUser);
        car.setCarId(1);
        car.setId(1L);
        cars.add(car);

        Mockito.doNothing().when(carService).delete(id);
        Mockito.when(carService.cars(mockUser)).thenReturn(cars);
        ReflectionTestUtils.setField(controller,"currentUser",mockUser);
        mockMvc.perform(get("/del/{carid}",id)).andExpect(status().isOk()).andExpect(view().name("CART"));
        Mockito.verify(carService).cars(mockUser);
        Mockito.verify(carService).delete(id);
    }

    @Test
    public void homePageReturnTest() throws Exception{
        mockMvc.perform(get("/html")).andExpect(view().name("HOME")).andExpect(status().isOk());
    }

    @Test
    public void singUpCredentialPageReturnTest() throws Exception{
        mockMvc.perform(get("/signupPage")).andExpect(status().isOk()).andExpect(view().name("SIGNUP"));
    }

    @Test
    public void loginCredentialPageReturnTest() throws Exception{
        mockMvc.perform(get("/loginPage")).andExpect(status().isOk()).andExpect(view().name("LOGIN"));
    }

    @Test
    public void cartPageReturnTest()throws Exception{
        mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("DASHBOARD"));
    }
}