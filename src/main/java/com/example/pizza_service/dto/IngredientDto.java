package com.example.pizza_service.dto;

import com.example.pizza_service.dto_validation.custom_constraints.NotEmptyObject;
import com.example.pizza_service.dto_validation.groups.OnCreate;
import com.example.pizza_service.dto_validation.groups.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@NotEmptyObject(groups = {OnCreate.class, OnUpdate.class})
public class IngredientDto {
    @NotNull(groups = {OnCreate.class})
    private String name;
}
