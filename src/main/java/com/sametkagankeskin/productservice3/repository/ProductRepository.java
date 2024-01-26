package com.sametkagankeskin.productservice3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sametkagankeskin.productservice3.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
