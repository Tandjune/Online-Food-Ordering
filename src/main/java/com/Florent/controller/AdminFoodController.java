package com.Florent.controller;

import com.Florent.model.Food;
import com.Florent.model.Restaurant;
import com.Florent.request.CreateFoodRequest;
import com.Florent.response.MessageResponse;
import com.Florent.service.FoodService;
import com.Florent.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id) throws Exception {
        foodService.deleteFood(id);

        MessageResponse message = new MessageResponse();
        message.setMessage("food deleted successfully");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<MessageResponse> updateFoodAvailabilityStatus(@PathVariable Long id) throws Exception {
        foodService.updateAvailabilityStatus(id);

        MessageResponse message = new MessageResponse();
        message.setMessage("updated successfully");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
