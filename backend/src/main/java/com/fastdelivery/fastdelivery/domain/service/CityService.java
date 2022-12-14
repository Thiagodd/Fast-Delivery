package com.fastdelivery.fastdelivery.domain.service;

import com.fastdelivery.fastdelivery.domain.model.City;
import com.fastdelivery.fastdelivery.domain.model.State;
import com.fastdelivery.fastdelivery.domain.repository.CityRepository;
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
public class CityService {

    String MSG_ENTITY_NOT_FOUND = "[custom] Não foi possivel encontrar uma cidade com o ID fornecido : %s";
    String MSG_ENTITY_IN_USE = "[custom] Não foi possivel deletar a cidade com o ID fornecido, pois ele está em uso : %s";

    private final CityRepository cityRepository;
    private final StateService stateService;

    public CityService(CityRepository cityRepository, StateService stateService) {
        this.cityRepository = cityRepository;
        this.stateService = stateService;
    }

    @Transactional(readOnly = true)
    public Page<City> findAll(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public City findById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id)));
    }

    @Transactional
    public City insert(City city) {
        State currentState = stateService.findById(city.getState().getId());
        city.setState(currentState);
        return cityRepository.save(city);
    }

    @Transactional
    public City update(Long id, City city) {
        try {
            City currentCity = this.findById(id);
            BeanUtils.copyProperties(city, currentCity, "id");
            return this.insert(currentCity);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND));
        }
    }

    public void deleteById(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        } catch (DataIntegrityViolationException exception) {
            throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }
}
