package com.example.car.service;

import com.example.car.model.User;
import com.example.car.repository.USERREPO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final USERREPO userrepo;
    public UserService (USERREPO userrepo,BCryptPasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
        this.userrepo=userrepo;
    }

    public void save(String name,String number,String password ){
        User user=new User();
        user.setName(name);
        user.setMobile(number);
        user.setPassword(passwordEncoder.encode(password));
         userrepo.save(user);
    }

    public User validateUser(String mobile,String password){
        User user= userrepo.findByMobile(mobile);
        if (passwordEncoder.matches(password,user.getPassword())){
            return user;
        }
        return null;
    }
}