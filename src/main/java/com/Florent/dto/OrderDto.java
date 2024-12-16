package com.Florent.dto;

import com.Florent.model.Address;
import com.Florent.model.OrderItem;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Embeddable
public class OrderDto {
    private String customerName;
    private Address deliveryAddress;
    private String restaurantName;
    private Address restaurantAddress;
    private Long totalAmount;
    private String orderStatus;
    private Date createAt;
    private List<OrderItem> items;
    private int totalItem;
    private Long totalPrice;
}
