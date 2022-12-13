package com.fastdelivery.fastdelivery.domain.repository;

import com.fastdelivery.fastdelivery.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}