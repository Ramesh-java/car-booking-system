package com.example.car.service;

import com.example.car.model.User;
import com.example.car.repository.USERREPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private USERREPO userrepo;

    public User save(User user){
        return userrepo.save(user);
    }

    public User getUser(String mobile){
        return userrepo.findByMobile(mobile);
    }


}
