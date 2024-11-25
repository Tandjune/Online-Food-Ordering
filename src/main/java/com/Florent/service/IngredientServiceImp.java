package com.Florent.service;

import com.Florent.model.IngredientCategory;
import com.Florent.model.IngredientsItem;
import com.Florent.model.Restaurant;
import com.Florent.repository.IngredientCategoryRepository;
import com.Florent.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientService {

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = new IngredientCategory();
        category.setName(name);
        category.setRestaurant(restaurant);

        ingredientCategoryRepository.save(category);
        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> optionalIngredientCategory = ingredientCategoryRepository.findById(id);

        if (optionalIngredientCategory.isEmpty()) {
            throw new Exception("No Ingredient Category found with the id:" + id);
        }
        return optionalIngredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategoryById(categoryId);

        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(ingredientCategory);

        IngredientsItem ingredient = ingredientItemRepository.save(ingredientsItem);
        ingredientCategory.getIngredients().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepository.findById(id);
        if (optionalIngredientsItem.isEmpty()) {
            throw new Exception("No ingredient item found with the id:" + id);
        }
        IngredientsItem ingredient = optionalIngredientsItem.get();
        ingredient.setInStoke(!ingredient.isInStoke());

        return ingredientItemRepository.save(ingredient);
    }
}
