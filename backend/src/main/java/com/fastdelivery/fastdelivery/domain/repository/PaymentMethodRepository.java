package com.fastdelivery.fastdelivery.domain.repository;

import com.fastdelivery.fastdelivery.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}