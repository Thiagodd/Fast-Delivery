package com.fastdelivery.fastdelivery.domain.service;

import com.fastdelivery.fastdelivery.domain.model.State;
import com.fastdelivery.fastdelivery.domain.repository.StateRepository;
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
public class StateService {

    String MSG_ENTITY_NOT_FOUND = "[custom] Não foi possivel encontrar um estado com o ID fornecido : %s";
    String MSG_ENTITY_IN_USE = "[custom] Não foi possivel deletar o estado com o ID fornecido, pois ele está em uso : %s";

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Transactional(readOnly = true)
    public Page<State> findAll(Pageable pageable) {
        return stateRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public State findById(Long id) {
        return stateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id)));
    }

    @Transactional
    public State insert(State state) {
        return stateRepository.save(state);
    }

    @Transactional
    public State update(Long id, State state) {
        try {
            State currentState = this.findById(id);
            BeanUtils.copyProperties(state, currentState, "id");
            return this.insert(currentState);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND));
        }
    }

    public void deleteById(Long id) {
        try {
            stateRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        } catch (DataIntegrityViolationException exception) {
            throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }
}
