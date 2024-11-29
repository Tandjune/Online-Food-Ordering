package com.Florent.controller;

import com.Florent.model.IngredientCategory;
import com.Florent.model.IngredientsItem;
import com.Florent.request.IngredientCategoryRequest;
import com.Florent.request.IngredientItemRequest;
import com.Florent.response.MessageResponse;
import com.Florent.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req) throws Exception {
        IngredientCategory ingredientCategory = ingredientService.createIngredientCategory(req.getName(), req.getRestaurantId());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest req) throws Exception {
        IngredientsItem ingredientItem = ingredientService.createIngredientItem(req.getRestaurantId(), req.getIngredientName(), req.getCategoryId());

        return new ResponseEntity<>(ingredientItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStoke(@PathVariable Long id) throws Exception {
        IngredientsItem ingredientItem = ingredientService.updateStock(id);

        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageResponse> deleteIngredientItem(@PathVariable Long id) throws Exception {
        ingredientService.deleteIngredientItem(id);

        MessageResponse message = new MessageResponse();
        message.setMessage("Ingredient item deleted successfully!");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(@PathVariable Long id) throws Exception {
        List<IngredientsItem> ingredientItems = ingredientService.findRestaurantsIngredients(id);

        return new ResponseEntity<>(ingredientItems, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long id) throws Exception {
        List<IngredientCategory> ingredientItemCategories = ingredientService.findIngredientCategoryByRestaurantId(id);

        return new ResponseEntity<>(ingredientItemCategories, HttpStatus.OK);
    }
}
