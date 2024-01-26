package com.sametkagankeskin.discountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sametkagankeskin.discountservice.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    Optional<Discount> findByCodeAndCategoryId(String code, Integer categoryId);
}
