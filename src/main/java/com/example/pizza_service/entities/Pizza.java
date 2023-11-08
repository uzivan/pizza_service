package com.example.pizza_service.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizza")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "price", "name", "ingredients"})
public class Pizza {
    @Id
    @TableGenerator(name = "pizza_gen", table = "gen_id_pizza", pkColumnName = "gen_name", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "pizza_gen")
    private int id;
    private int price;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ingredients_pizza"
            , joinColumns = @JoinColumn(name = "id_pizza")
            , inverseJoinColumns = @JoinColumn(name = "id_ingredient"))
    @JsonProperty(value = "ingredients")
    private List<Ingredient> ingredients = new ArrayList<>();
}
