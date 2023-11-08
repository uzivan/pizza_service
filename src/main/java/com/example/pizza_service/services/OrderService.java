package com.example.pizza_service.services;

import com.example.pizza_service.entities.Order;
import com.example.pizza_service.entities.Pizza;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public List<Order> findAll();
    public Optional<Order> findById(int id);
    public Order save(Order order);

    public void delete(Order order);
}
