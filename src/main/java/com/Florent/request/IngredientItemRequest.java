package com.Florent.request;

import lombok.Data;

@Data
public class IngredientItemRequest {
    Long restaurantId;
    String ingredientName;
    Long categoryId;
}
