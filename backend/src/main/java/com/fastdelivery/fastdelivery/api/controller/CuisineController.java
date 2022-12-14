package com.fastdelivery.fastdelivery.api.controller;

import com.fastdelivery.fastdelivery.domain.model.Cuisine;
import com.fastdelivery.fastdelivery.domain.service.CuisineService;
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
@RequestMapping("cuisines")
public class CuisineController {

    private final CuisineService cuisineService;

    public CuisineController(CuisineService cuisineService) {
        this.cuisineService = cuisineService;
    }

    @GetMapping
    public ResponseEntity<Page<Cuisine>> findAll(Pageable pageable){
        Page<Cuisine> cuisines = cuisineService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cuisines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            Cuisine cuisine = cuisineService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(cuisine);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Cuisine> insert(@RequestBody Cuisine cuisine){
        cuisine = cuisineService.insert(cuisine);
        return ResponseEntity.status(HttpStatus.OK).body(cuisine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cuisine cuisine){
        try{
            cuisine = cuisineService.update(id, cuisine);
            return ResponseEntity.status(HttpStatus.OK).body(cuisine);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try{
            cuisineService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }catch (EntityInUseException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
