package com.example.pizza_service.services;

import com.example.pizza_service.entities.Ingredient;
import com.example.pizza_service.repositories.IngredientRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService{
    @Autowired
    private IngredientRepository ingredientRepository;
    @Override
    public List<Ingredient> findAll(){
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> findById(Integer id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
    @Override
    public void delete(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }
}
