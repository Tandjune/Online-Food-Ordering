package com.Florent.service;

import com.Florent.dto.OrderDto;
import com.Florent.model.*;
import com.Florent.repository.AddressRepository;
import com.Florent.repository.OrderItemRepository;
import com.Florent.repository.OrderRepository;
import com.Florent.repository.UserRepository;
import com.Florent.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        Date creatDate = new Date();

        Address deliveryAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepository.save(deliveryAddress);

        if (!containsAddress(savedAddress, user.getAddresses())) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreateAt(creatDate);
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartById(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        Long totalPrice = cartService.calculateCartTotals(cart);

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerName(user.getFullName());
        orderDto.setDeliveryAddress(savedAddress);
        orderDto.setRestaurantName(restaurant.getName());
        orderDto.setRestaurantAddress(restaurant.getAddress());
        orderDto.setOrderStatus("PENDING");
        orderDto.setCreateAt(creatDate);
        orderDto.setItems(orderItems);
        orderDto.setTotalPrice(totalPrice);


        return savedOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }

        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new Exception("Oder not found with the id:" + orderId);
        }
        return optionalOrder.get();
    }

    public boolean containsAddress(Address orderAddress, List<Address> userAddresses) {

        for (Address userAddress : userAddresses) {
            boolean sameStreetAddress = orderAddress.getStreetAddress().equals(userAddress.getStreetAddress());
            boolean sameCity = orderAddress.getCity().equals(userAddress.getCity());
            boolean sameProvince = orderAddress.getStateProvince().equals(userAddress.getStateProvince());
            boolean samePostalCode = orderAddress.getPostalCode().equals(userAddress.getPostalCode());
            boolean sameCountry = orderAddress.getCountry().equals(userAddress.getCountry());

            boolean sameAddr = sameStreetAddress && sameCity && sameProvince && samePostalCode && sameCountry;

            if (sameAddr) {
                return true;
            }
        }
        return false;
    }
}
