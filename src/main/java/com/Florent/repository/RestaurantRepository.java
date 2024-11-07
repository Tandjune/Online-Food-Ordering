package com.Florent.repository;

import com.Florent.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r from Restaurant r where lower(r.name) LIKE lower(concat('%', :query, '%') ) " + "or lower(r.cuisineType) like lower(concat('%', :query, '%') )")
    List<Restaurant> findBySearchQuery(String query);

    Restaurant finByOwnerId(Long userId);
}
