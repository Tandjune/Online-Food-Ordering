package com.Florent.service;

import com.Florent.dto.OrderDto;
import com.Florent.model.Order;
import com.Florent.model.User;
import com.Florent.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public OrderDto createOrder(OrderRequest order, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUsersOrder(Long userId);

    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus);

    public Order findOrderById(Long orderId) throws Exception;
}
