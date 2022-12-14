package com.fastdelivery.fastdelivery.domain.service;

import com.fastdelivery.fastdelivery.domain.model.PaymentMethod;
import com.fastdelivery.fastdelivery.domain.repository.PaymentMethodRepository;
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
public class PaymentMethodService {

    String MSG_ENTITY_NOT_FOUND = "[custom] Não foi possivel encontrar um método de pagamento com o ID fornecido : %s";
    String MSG_ENTITY_IN_USE = "[custom] Não foi possivel deletar um método de pagamento com o ID fornecido, pois ele está em uso : %s";

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Transactional(readOnly = true)
    public Page<PaymentMethod> findAll(Pageable pageable){
        return paymentMethodRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public PaymentMethod findById(Long id){
        return paymentMethodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(MSG_ENTITY_NOT_FOUND, id)
        ));
    }

    @Transactional
    public PaymentMethod insert(PaymentMethod paymentMethod){
        return paymentMethodRepository.save(paymentMethod);
    }

    @Transactional
    public PaymentMethod update(Long id, PaymentMethod paymentMethod){
        try{
            PaymentMethod currentPaymentMethod =this.findById(id);
            BeanUtils.copyProperties(paymentMethod, currentPaymentMethod, "id");
            return this.insert(currentPaymentMethod);
        }catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND));
        }
    }

    public void deleteById(Long id){
        try{
            paymentMethodRepository.deleteById(id);
        }catch (EmptyResultDataAccessException exception){
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        }catch (DataIntegrityViolationException exception){
            throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }
}
