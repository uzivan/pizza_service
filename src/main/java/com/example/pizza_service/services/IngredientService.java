package com.example.pizza_service.services;

import com.example.pizza_service.entities.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    public List<Ingredient> findAll();
    public Optional<Ingredient> findById(Integer id);
    public Ingredient save(Ingredient ingredient);

    public void delete(Ingredient ingredient);
}
