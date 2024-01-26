package com.sametkagankeskin.productservice3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sametkagankeskin.productservice3.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
