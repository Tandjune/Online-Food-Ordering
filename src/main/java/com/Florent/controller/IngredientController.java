package com.Florent.controller;

import com.Florent.model.IngredientCategory;
import com.Florent.model.IngredientsItem;
import com.Florent.model.User;
import com.Florent.request.IngredientCategoryRequest;
import com.Florent.request.IngredientItemRequest;
import com.Florent.service.IngredientService;
import com.Florent.service.UserService;
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

    @Autowired
    private UserService userService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt); // added, was empty
        IngredientCategory ingredientCategory = ingredientService.createIngredientCategory(req.getName(), req.getRestaurantId());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt); // added, was empty
        IngredientsItem ingredientItem = ingredientService.createIngredientItem(req.getRestaurantId(), req.getIngredientName(), req.getCategoryId());

        return new ResponseEntity<>(ingredientItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStoke(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt); // added, was empty
        IngredientsItem ingredientItem = ingredientService.updateStock(id);

        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt); // added, was empty
        List<IngredientsItem> ingredientItems = ingredientService.findRestaurantsIngredients(id);

        return new ResponseEntity<>(ingredientItems, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt); // added, was empty
        List<IngredientCategory> ingredientItemCategories = ingredientService.findIngredientCategoryByRestaurantId(id);

        return new ResponseEntity<>(ingredientItemCategories, HttpStatus.OK);
    }
}
