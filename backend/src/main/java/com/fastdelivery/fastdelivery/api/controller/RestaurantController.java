package com.fastdelivery.fastdelivery.api.controller;

import com.fastdelivery.fastdelivery.domain.model.Restaurant;
import com.fastdelivery.fastdelivery.domain.service.RestaurantService;
import com.fastdelivery.fastdelivery.domain.service.exceptions.EntityInUseException;
import com.fastdelivery.fastdelivery.domain.service.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<Page<Restaurant>> findAll(Pageable pageable){
        Page<Restaurant> restaurants = restaurantService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            Restaurant restaurant = restaurantService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(restaurant);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Restaurant restaurant){
        try{
            restaurant = restaurantService.insert(restaurant);
            return ResponseEntity.status(HttpStatus.OK).body(restaurant);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant){
        try{
            restaurant = restaurantService.update(id, restaurant);
            return ResponseEntity.status(HttpStatus.OK).body(restaurant);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try{
            restaurantService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }catch (EntityInUseException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
