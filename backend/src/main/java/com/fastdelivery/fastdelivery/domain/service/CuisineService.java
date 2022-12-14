package com.fastdelivery.fastdelivery.domain.service;

import com.fastdelivery.fastdelivery.domain.model.Cuisine;
import com.fastdelivery.fastdelivery.domain.repository.CuisineRepository;
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
public class CuisineService {

    String MSG_ENTITY_NOT_FOUND = "[custom] Não foi possivel encontrar uma cozinha com o ID fornecido : %s";
    String MSG_ENTITY_IN_USE = "[custom] Não foi possivel deletar uma cozinha com o ID fornecido, pois ele está em uso : %s";

    private final CuisineRepository cuisineRepository;

    public CuisineService(CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }

    @Transactional(readOnly = true)
    public Page<Cuisine> findAll(Pageable pageable){
        return cuisineRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Cuisine findById(Long id){
        return cuisineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(MSG_ENTITY_NOT_FOUND, id)
        ));
    }

    @Transactional
    public Cuisine insert(Cuisine cuisine){
        return cuisineRepository.save(cuisine);
    }

    @Transactional
    public Cuisine update(Long id, Cuisine cuisine){
        try{
            Cuisine currentCuisine = this.findById(id);
            BeanUtils.copyProperties(cuisine, currentCuisine, "id");
            return this.insert(currentCuisine);
        }catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND));
        }
    }

    public void deleteById(Long id){
        try{
            cuisineRepository.deleteById(id);
        }catch (EmptyResultDataAccessException exception){
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        }catch (DataIntegrityViolationException exception){
            throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }
}
