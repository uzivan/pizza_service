package com.example.pizza_service.controllers;

import com.example.pizza_service.dto.PizzaDto;
import com.example.pizza_service.dto_validation.groups.OnCreate;
import com.example.pizza_service.dto_validation.groups.OnUpdate;
import com.example.pizza_service.entities.Ingredient;
import com.example.pizza_service.entities.Pizza;
import com.example.pizza_service.services.IngredientService;
import com.example.pizza_service.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/pizza")
public class PizzaController {
    private static final Class<OnCreate> CREATE_OPTION = OnCreate.class;
    private static final Class<OnUpdate> UPDATE_OPTION = OnUpdate.class;
    private static final String NO_SUCH_PIZZA_MESSAGE = "no pizza with such ID is present";
    private static final String INVALID_ID_MESSAGE = "invalid ID; must be more than 0";
    private static final String NO_PROPERTIES_TO_UPDATE_MESSAGE = "no properties to update in request body";
    private static final String NO_SUCH_INGREDIENT_MESSAGE = "no such ingredient with provided ID: ";

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<Pizza>> showAll() {
        List<Pizza> pizzas = pizzaService.findAll();
        return ResponseEntity.ok(pizzaService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pizza> getPizza(@PathVariable("id") int id) {
        validatePizzaId(id);
        return pizzaService.findById(id).map(ResponseEntity::ok).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, NO_SUCH_PIZZA_MESSAGE));
    }
    @PostMapping(value = "/new")
    public ResponseEntity<Pizza> createPizza(@RequestBody PizzaDto pizzaDto){
        validatePizzaDto(pizzaDto, CREATE_OPTION);
        Pizza pizza = mapToPizza(pizzaDto);
        return ResponseEntity.ok(pizzaService.save(pizza));
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable("id") int id, @RequestBody PizzaDto pizzaDto){
        validatePizzaId(id);
        validatePizzaDto(pizzaDto, UPDATE_OPTION);
        Pizza pizza = checkForPresenceAndGetPizza(id);
        updateProduct(pizza, pizzaDto);
        pizzaService.save(pizza);
        return ResponseEntity.ok(pizza);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Pizza> deletePizza(@PathVariable("id") int id){
        validatePizzaId(id);
        Pizza deletepizza = checkForPresenceAndGetPizza(id);
        pizzaService.delete(deletepizza);
        return ResponseEntity.ok().build();
    }
    private void validatePizzaDto(PizzaDto pizzaDto, Class<?> option) {
        if (isNull(pizzaDto)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    NO_PROPERTIES_TO_UPDATE_MESSAGE);
        }

        if (isNull(option) || !option.equals(UPDATE_OPTION)) {
            option = CREATE_OPTION;
        }

        Set<ConstraintViolation<PizzaDto>> violations = validator.validate(pizzaDto, option);

        if (!violations.isEmpty()) {
            StringBuilder str = new StringBuilder();
            for (ConstraintViolation<PizzaDto> violation : violations) {
                str.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
            }
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, str.toString());
        }
    }
    private void validatePizzaId(Integer pizzaId) {
        if (isNull(pizzaId) || pizzaId < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_ID_MESSAGE);
        }
    }
    private Pizza mapToPizza(PizzaDto pizzaDto){
        Pizza pizza = new Pizza();
        pizza.setName(pizzaDto.getName());
        pizza.setPrice(pizzaDto.getPrice());
        pizza.setIngredients(dtoIngredientsMapToList(pizzaDto.getIngredientsId()));
        return pizza;
    }
    private List<Ingredient> dtoIngredientsMapToList(List<Integer> ingredients_id){
        List<Ingredient> ingredients = new ArrayList<>();
        for (Integer integer : ingredients_id) {
            Ingredient ingredient = checkForPresenceAndGetIngredient(integer);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private Ingredient checkForPresenceAndGetIngredient(Integer id){
        return ingredientService.findById(id).orElseThrow(
                ()->new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                NO_SUCH_INGREDIENT_MESSAGE + id));
    }

    private Pizza checkForPresenceAndGetPizza(Integer id){
        return pizzaService.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, NO_SUCH_PIZZA_MESSAGE));
    }

    private void updateProduct(Pizza editable, PizzaDto changes) {
        if(nonNull(changes.getName())){
            editable.setName(changes.getName());
        }
        if(nonNull(changes.getPrice())){
            editable.setPrice(changes.getPrice());
        }
        if(nonNull(changes.getIngredientsId())){
            editable.setIngredients(dtoIngredientsMapToList(changes.getIngredientsId()));
        }
    }
}
