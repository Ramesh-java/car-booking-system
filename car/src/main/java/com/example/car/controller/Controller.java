package com.example.car.controller;
import com.example.car.model.Car;
import com.example.car.model.Cars;
import com.example.car.model.User;
import com.example.car.service.CarService;
import com.example.car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;
    User currentUser=null;

    static List<Cars> list;
    public static void carStarter(){
        list =new ArrayList<>();
        list.add(new Cars("THE 3 GRAN LIMOUSINE","https://static.smoss.apac.bmw.cloud/bmw-admin-portal/bmw-admin-portal-productcatalog-prod/1704817696718_7inNewProject(6).png",8000000,1));
        list.add(new Cars("THE 2","https://static.smoss.apac.bmw.cloud/bmw-admin-portal/bmw-admin-portal-productcatalog-prod/1700464218274_X1inNewProject(5).png",7600000,2));
        list.add(new Cars("THE X1","https://static.smoss.apac.bmw.cloud/bmw-admin-portal/bmw-admin-portal-productcatalog-prod/1679984621950_3in3GL.png",9000000,3));
        list.add(new Cars("THE 7","https://static.smoss.apac.bmw.cloud/bmw-admin-portal/bmw-admin-portal-productcatalog-prod/1641183942015_2in2gc.png",7000000,4));
    }


    @GetMapping("html") //opening page after starting the application , returns home page
    public String home(){
        carStarter();
        return "HOME";
    }


    @GetMapping("signupPage") //after clicking signup button in home page , this method will return signup page
    public String signUp(){
        return "SIGNUP";
    }


    @PostMapping("/Signed")//this method will return login page after the user enters details and clicking the signup button
    public String afterSign(@RequestParam("name")String name,@RequestParam("mobile") String number,@RequestParam("password") String password ){
        User user=new User();
        user.setName(name);
        user.setMobile(number);
        user.setPassword(password);

        userService.save(user);

        return "LOGIN";
    }


    @GetMapping("loginPage") //after clicking login button in home page ,this method will return login page
    public String log(){
        return "LOGIN";
    }


    @PostMapping("/logged") //this method recives the mobile number and password and allows the user to dashboard, if details are wrong redirects to same page
    public String afterLogin(@RequestParam("mobile") String number,@RequestParam("password") String password){
        User user=userService.getUser(number);
        if (user!=null && user.getPassword().equals(password)){
            currentUser=user;
            return "DASHBOARD";
        }return "LOGIN";

    }


    @GetMapping("/add/{carid}") //this method gets triggered after user tries to add a car to thier cart
    public String addToCart(@PathVariable int carid){
        Cars carFromCars=carService.getCarById(carid,list);
        if (carFromCars!=null){
            Car car=new Car();
            car.setURL(carFromCars.getURL());
            car.setCarId(carid);
            car.setModel(carFromCars.getModel());
            car.setPrice((int)carFromCars.getPrice());
            car.setUser(currentUser);
            carService.save(car);
        }
        return "DASHBOARD";
    }


    @GetMapping("/cart") //when the user clicks view cart button in the dashboard, this method will return cart page
    public String cart(Model model){
        List<Car> cars=carService.cars(currentUser);
        model.addAttribute("name",currentUser.getName());
        model.addAttribute("cars",cars);
        return "CART";
    }


    @GetMapping("/del/{carid}") //if the user decides to remove a car from his cart, this method will get called
    public String delete(@PathVariable Long carid,Model model){
        carService.delete(carid);
        List<Car> cars=carService.cars(currentUser);
        model.addAttribute("name",currentUser.getName());
        model.addAttribute("cars",cars);
        return "CART";
    }


    @GetMapping("/home") //method for returning to dashboard from the cart page
    public String cartHome(){
        return "DASHBOARD";
    }
}