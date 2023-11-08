package com.example.pizza_service.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="ingredients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    @TableGenerator(name = "ingredient_gen", table = "gen_id_ingredients", pkColumnName = "gen_name", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ingredient_gen")
    private int id;
    private String name;
}
