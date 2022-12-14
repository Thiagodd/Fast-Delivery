package com.fastdelivery.fastdelivery.api.controller;

import com.fastdelivery.fastdelivery.domain.model.State;
import com.fastdelivery.fastdelivery.domain.service.StateService;
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
@RequestMapping("states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<Page<State>> findAll(Pageable pageable){
        Page<State> states = stateService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(states);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            State state = stateService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(state);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<State> insert(@RequestBody State state){
        state = stateService.insert(state);
        return ResponseEntity.status(HttpStatus.OK).body(state);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody State state){
        try{
            state = stateService.update(id, state);
            return ResponseEntity.status(HttpStatus.OK).body(state);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try{
            stateService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }catch (EntityInUseException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
