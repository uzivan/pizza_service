package com.example.pizza_service.services;

import com.example.pizza_service.entities.User;

public interface UserService {
    public User findByUsername(String username);
}
