package com.sametkagankeskin.discountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sametkagankeskin.discountservice.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByExternalId(String externalId);

}
