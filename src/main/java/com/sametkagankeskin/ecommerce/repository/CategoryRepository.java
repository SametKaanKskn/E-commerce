package com.sametkagankeskin.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sametkagankeskin.ecommerce.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByCategoryNameIgnoreCase(String categoryName);

    Optional<Category> findByCategoryName(String categoryName);

}
