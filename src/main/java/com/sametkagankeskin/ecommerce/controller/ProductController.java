package com.sametkagankeskin.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sametkagankeskin.ecommerce.model.dto.ProductDto;
import com.sametkagankeskin.ecommerce.model.vm.AddProductVm;
import com.sametkagankeskin.ecommerce.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products/")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/{productId}")
    public ProductDto getProductById(@PathVariable("productId") int productId) {
        return productService.getProductById(productId);
    }

    @DeleteMapping(value = "/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteProductById(@PathVariable("productId") int productId) {
        return productService.deleteProductById(productId);
    }

    @PostMapping(value = "")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public int addNewProduct(@Valid @RequestBody AddProductVm productVm) {
        return productService.addNewProduct(productVm);
    }

    @GetMapping(value = "/search/{categoryName}")
    public List<ProductDto> getProductsByCategoryName(@PathVariable("categoryName") String categoryName) {
        return productService.getProductsByCategoryName(categoryName);
    }

    @GetMapping(value = "/search/price/biggerThan/{productPrice}")
    public List<ProductDto> getProductsWhosePriceBigger(@PathVariable("productPrice") double productPrice) {
        return productService.getProductsWhosePriceBigger(productPrice);
    }

    @GetMapping(value = "/search/price/lowerThan/{productPrice}")
    public List<ProductDto> getProductsWhosePriceLower(@PathVariable("productPrice") double productPrice) {
        return productService.getProductsWhosePriceLower(productPrice);
    }

}
