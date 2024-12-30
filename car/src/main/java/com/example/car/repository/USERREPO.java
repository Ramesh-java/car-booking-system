package com.example.car.repository;

import com.example.car.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface USERREPO extends JpaRepository<User,Long> {
    public User findByMobile(String mobile);
}
