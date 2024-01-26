package com.sametkagankeskin.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sametkagankeskin.ecommerce.model.entity.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {

}
