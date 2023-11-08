package com.example.pizza_service.repositories;

import com.example.pizza_service.entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}
