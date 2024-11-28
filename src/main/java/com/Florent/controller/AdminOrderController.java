package com.Florent.controller;

import com.Florent.model.Order;
import com.Florent.model.User;
import com.Florent.service.OrderService;
import com.Florent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long id, @RequestParam(required = false) String orderStatus, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantsOrder(id, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @PathVariable String orderStatus, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(orderId, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
