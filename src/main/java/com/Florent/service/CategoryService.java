package com.Florent.service;

import com.Florent.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String name, Long userID) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long id) throws Exception;

    public Category findCategoryById(Long id) throws Exception;
}
