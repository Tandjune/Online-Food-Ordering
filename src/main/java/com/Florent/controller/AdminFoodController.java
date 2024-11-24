package com.Florent.controller;

import com.Florent.model.Food;
import com.Florent.model.Restaurant;
import com.Florent.model.User;
import com.Florent.request.CreateFoodRequest;
import com.Florent.response.MessageResponse;
import com.Florent.service.FoodService;
import com.Florent.service.RestaurantService;
import com.Florent.service.UserService;
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
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);

        MessageResponse message = new MessageResponse();
        message.setMessage("food deleted successfully");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<MessageResponse> updateFoodAvailabilityStatus(@PathVariable Long id,
                                                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.updateAvailabilityStatus(id);

        MessageResponse message = new MessageResponse();
        message.setMessage("updated successfully");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
