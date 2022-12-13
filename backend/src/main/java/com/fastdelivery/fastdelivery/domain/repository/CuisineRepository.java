package com.fastdelivery.fastdelivery.domain.repository;

import com.fastdelivery.fastdelivery.domain.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
}