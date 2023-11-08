package com.example.pizza_service.controllers;

import com.example.pizza_service.dto.IngredientDto;
import com.example.pizza_service.dto.OrderDto;
import com.example.pizza_service.dto_validation.groups.OnCreate;
import com.example.pizza_service.dto_validation.groups.OnUpdate;
import com.example.pizza_service.entities.Ingredient;
import com.example.pizza_service.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/ingredients")
public class IngredientController{
    private static final Class<OnCreate> CREATE_OPTION = OnCreate.class;
    private static final Class<OnUpdate> UPDATE_OPTION = OnUpdate.class;
    private static final String NO_SUCH_INGREDIENT_MESSAGE = "no order with such ID is present";

    private static final String NO_SUCH_PIZZA_MESSAGE = "no such pizza with provided ID: ";
    private static final String INVALID_ID_MESSAGE = "invalid ID; must be more than 0";
    private static final String NO_PROPERTIES_TO_UPDATE_MESSAGE = "no properties to update in request body";
//    private static final String NO_SUCH_INGREDIENT_MESSAGE = "no such ingredient with provided ID: ";
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    @Autowired
    public IngredientService ingredientService;
    @GetMapping
    private ResponseEntity<List<Ingredient>> showAll(){
        return ResponseEntity.ok(ingredientService.findAll());
    }
    @GetMapping(value = "/{id}")
    private ResponseEntity<Ingredient> getIngredient(@PathVariable("id") int id){
        validateOrderId(id);
        return ingredientService.findById(id).map(ResponseEntity::ok).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.BAD_REQUEST,NO_SUCH_INGREDIENT_MESSAGE));
    }
    @PostMapping(value = "/new")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody IngredientDto ingredientDto){
        validateOrderDto(ingredientDto, CREATE_OPTION);
        Ingredient ingredient = mapToIngredient(ingredientDto);
        ingredientService.save(ingredient);
        return ResponseEntity.ok(ingredient);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable("id") int id, @RequestBody IngredientDto ingredientDto){
        validateOrderId(id);
        validateOrderDto(ingredientDto, UPDATE_OPTION);
        Ingredient ingredient = checkForPresentAndGetIngredient(id);
        updateIngredient(ingredient, ingredientDto);
        ingredientService.save(ingredient);
        return ResponseEntity.ok(ingredient);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable("id") int id){
        validateOrderId(id);
        Ingredient ingredient = checkForPresentAndGetIngredient(id);
        ingredientService.delete(ingredient);
        return ResponseEntity.ok().build();
    }
    private void validateOrderDto(IngredientDto ingredientDto, Class<?> option) {
        if (isNull(ingredientDto)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    NO_PROPERTIES_TO_UPDATE_MESSAGE);
        }

        if (isNull(option) || !option.equals(UPDATE_OPTION)) {
            option = CREATE_OPTION;
        }

        Set<ConstraintViolation<IngredientDto>> violations = validator.validate(ingredientDto, option);

        if (!violations.isEmpty()) {
            StringBuilder str = new StringBuilder();
            for (ConstraintViolation<IngredientDto> violation : violations) {
                str.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
            }
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, str.toString());
        }
    }
    private void validateOrderId(Integer orderId) {
        if (isNull(orderId) || orderId < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_ID_MESSAGE);
        }
    }
    private Ingredient mapToIngredient(IngredientDto ingredientDto){
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientDto.getName());
        return ingredient;
    }
    private Ingredient checkForPresentAndGetIngredient(Integer id){
        return ingredientService.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.BAD_REQUEST, NO_SUCH_INGREDIENT_MESSAGE));
    }
    private void updateIngredient(Ingredient ingredient, IngredientDto ingredientDto){
        if(nonNull(ingredientDto.getName())){
            ingredient.setName(ingredientDto.getName());
        }
    }
}
