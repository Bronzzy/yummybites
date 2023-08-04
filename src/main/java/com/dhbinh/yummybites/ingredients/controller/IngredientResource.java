package com.dhbinh.yummybites.ingredients.controller;

import com.dhbinh.yummybites.base.exception.ErrorMessage;
import com.dhbinh.yummybites.ingredients.service.IngredientService;
import com.dhbinh.yummybites.ingredients.service.dto.IngredientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.net.URI;

@RestController
@Validated
@RequestMapping(value = "/ingredients")
public class IngredientResource {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<IngredientDTO> create(@Valid @RequestBody IngredientDTO ingredientDTO) {
        IngredientDTO dto = ingredientService.create(ingredientDTO);
        return ResponseEntity.created(URI.create("/api/ingredients" + dto.getID())).body(dto);
    }

    @PutMapping("/{ingredient-id}/add")
    public ResponseEntity<IngredientDTO> add(@PathVariable("ingredient-id") Long ID,
                                             @RequestParam("quantity")
                                             @Min(value = 0, message = ErrorMessage.INGREDIENT_QUANTITY_LESS_THAN_ZERO)
                                             Double quantity) {
        return ResponseEntity.ok(ingredientService.add(ID, quantity));
    }

    @PutMapping("/{ingredient-id}/deducted")
    public ResponseEntity<IngredientDTO> deducted(@PathVariable("ingredient-id") Long ID,
                                                  @RequestParam("quantity") @Min(value = 0, message = ErrorMessage.INGREDIENT_QUANTITY_LESS_THAN_ZERO)
                                                  Double quantity) {
        return ResponseEntity.ok(ingredientService.deducted(ID, quantity));
    }
}
