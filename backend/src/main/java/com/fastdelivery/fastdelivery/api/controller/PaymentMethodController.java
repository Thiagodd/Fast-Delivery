package com.fastdelivery.fastdelivery.api.controller;

import com.fastdelivery.fastdelivery.domain.model.PaymentMethod;
import com.fastdelivery.fastdelivery.domain.service.PaymentMethodService;
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
@RequestMapping("payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public ResponseEntity<Page<PaymentMethod>> findAll(Pageable pageable){
        Page<PaymentMethod> paymentMethods = paymentMethodService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(paymentMethods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            PaymentMethod paymentMethod = paymentMethodService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(paymentMethod);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<PaymentMethod> insert(@RequestBody PaymentMethod paymentMethod){
        paymentMethod = paymentMethodService.insert(paymentMethod);
        return ResponseEntity.status(HttpStatus.OK).body(paymentMethod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PaymentMethod paymentMethod){
        try{
            paymentMethod = paymentMethodService.update(id, paymentMethod);
            return ResponseEntity.status(HttpStatus.OK).body(paymentMethod);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try{
            paymentMethodService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }catch (EntityInUseException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
