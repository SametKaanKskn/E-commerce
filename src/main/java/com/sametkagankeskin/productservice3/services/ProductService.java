package com.sametkagankeskin.productservice3.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sametkagankeskin.productservice3.model.Product;
import com.sametkagankeskin.productservice3.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product add(Product product) {
        return repository.save(product);
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found"));
    }
}
