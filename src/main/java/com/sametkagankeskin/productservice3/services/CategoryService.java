package com.sametkagankeskin.productservice3.services;

import org.springframework.stereotype.Service;

import com.sametkagankeskin.productservice3.model.Category;
import com.sametkagankeskin.productservice3.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category add(Category category) {
        return repository.save(category);
    }
}
