package com.example.pizza_service.dto;

import com.example.pizza_service.dto_validation.custom_constraints.NotEmptyObject;
import com.example.pizza_service.dto_validation.groups.OnCreate;
import com.example.pizza_service.dto_validation.groups.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NotEmptyObject(groups = {OnCreate.class, OnUpdate.class})
public class PizzaDto {
    @NotNull(groups = {OnCreate.class})
    private String name;
    @Min(value = 0, groups = {OnCreate.class, OnUpdate.class})
    @Max(value = Integer.MAX_VALUE, groups = {OnCreate.class, OnUpdate.class})
    private Integer price;
    @NotNull(groups = {OnCreate.class})
    @Size(min = 1, groups = {OnCreate.class, OnUpdate.class})
    private List<@NotNull @Min(value = 1, groups = {OnCreate.class, OnUpdate.class}) Integer> ingredientsId;
}
