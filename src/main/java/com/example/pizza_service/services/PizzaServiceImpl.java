package com.example.pizza_service.services;

import com.example.pizza_service.entities.Pizza;
import com.example.pizza_service.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Override
    public List<Pizza> findAll(){
        return pizzaRepository.findAll();
    }
    @Override
    public Optional<Pizza> findById(int id) {
        return pizzaRepository.findById(id);
    }

    @Override
    public Pizza save(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public void delete(Pizza pizza) {
        pizzaRepository.delete(pizza);
    }

}
