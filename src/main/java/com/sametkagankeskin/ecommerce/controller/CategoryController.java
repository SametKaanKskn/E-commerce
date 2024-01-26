package com.sametkagankeskin.ecommerce.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sametkagankeskin.ecommerce.model.dto.CategoryDto;
import com.sametkagankeskin.ecommerce.model.vm.AddCategoryVm;
import com.sametkagankeskin.ecommerce.model.vm.UpdateCategoryVm;
import com.sametkagankeskin.ecommerce.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories/")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "all")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(value = "/{categoryId}")
    public CategoryDto getByCategoryId(@PathVariable("categoryId") int categoryId) {
        return categoryService.getByCategoryId(categoryId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public int addNewCategory(@Valid @RequestBody AddCategoryVm categoryVm) {
        return categoryService.addNewCategory(categoryVm);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteByCategoryId(@PathVariable("categoryId") int categoryId) {
        return categoryService.deleteByCategoryId(categoryId);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateCategory(@Valid @RequestBody UpdateCategoryVm categoryVm) {
        return categoryService.updateCategory(categoryVm);
    }
}
