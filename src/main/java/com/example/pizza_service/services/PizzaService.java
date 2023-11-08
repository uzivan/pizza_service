package com.example.pizza_service.services;

import com.example.pizza_service.entities.Ingredient;
import com.example.pizza_service.entities.Pizza;

import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;

public interface PizzaService {
    public List<Pizza> findAll();
    public Optional<Pizza> findById(int id);

    public Pizza save(Pizza pizza);

    public void delete(Pizza pizza);
}
