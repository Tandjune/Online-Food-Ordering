package com.Florent.controller;

import com.Florent.model.Food;
import com.Florent.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name) {
        List<Food> foods = foodService.searchFood(name);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable Long restaurantId,
                                                        @RequestParam boolean vegetarian,
                                                        @RequestParam boolean seasonal,
                                                        @RequestParam boolean nonveg,
                                                        @RequestParam(required = false) String category) {
        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegetarian, nonveg, seasonal, category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
