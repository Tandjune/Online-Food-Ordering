package com.Florent.repository;

import com.Florent.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {

    public List<IngredientCategory> findByRestaurantId(Long restaurantId);
}
