package com.fastdelivery.fastdelivery.domain.service;

import com.fastdelivery.fastdelivery.domain.model.Cuisine;
import com.fastdelivery.fastdelivery.domain.model.Restaurant;
import com.fastdelivery.fastdelivery.domain.repository.RestaurantRepository;
import com.fastdelivery.fastdelivery.domain.service.exceptions.EntityInUseException;
import com.fastdelivery.fastdelivery.domain.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class RestaurantService {

    String MSG_ENTITY_NOT_FOUND = "[custom] Não foi possivel encontrar um restaurante com o ID fornecido : %s";
    String MSG_ENTITY_IN_USE = "[custom] Não foi possivel deletar o restaurante com o ID fornecido, pois ele está em uso : %s";

    private final RestaurantRepository restaurantRepository;
    private final CuisineService cuisineService;

    public RestaurantService(RestaurantRepository restaurantRepository, CuisineService cuisineService) {
        this.restaurantRepository = restaurantRepository;
        this.cuisineService = cuisineService;
    }

    @Transactional(readOnly = true)
    public Page<Restaurant> findAll(Pageable pageable){
        return restaurantRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Restaurant findById(Long id){
        return restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(MSG_ENTITY_NOT_FOUND, id)
        ));
    }

    @Transactional
    public Restaurant insert(Restaurant restaurant){
        Cuisine currentCuisine = cuisineService.findById(restaurant.getCuisine().getId());
        restaurant.setCuisine(currentCuisine);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant update(Long id, Restaurant restaurant){
        try{
            Restaurant currentRestaurant = this.findById(id);
            BeanUtils.copyProperties(restaurant, currentRestaurant, "id");
            return this.insert(currentRestaurant);
        }catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND));
        }
    }

    public void deleteById(Long id){
        try{
            restaurantRepository.deleteById(id);
        }catch (EmptyResultDataAccessException exception){
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        }catch (DataIntegrityViolationException exception){
            throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }
}
