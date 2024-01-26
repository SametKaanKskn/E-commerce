package com.sametkagankeskin.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sametkagankeskin.ecommerce.model.dto.ProductDto;
import com.sametkagankeskin.ecommerce.model.dto.ShoppingCartDto;
import com.sametkagankeskin.ecommerce.model.vm.AddProductsInCartVm;
import com.sametkagankeskin.ecommerce.service.ShoppingCartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/carts/")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ShoppingCartDto> getAllCarts() {
        return shoppingCartService.getAllCarts();
    }

    @GetMapping(value = "/{cartId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ShoppingCartDto getCartByCartId(@PathVariable("cartId") int cartId) {
        return shoppingCartService.getCartByCartId(cartId);
    }

    @GetMapping(value = "/customers/{customerId}/cart")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ShoppingCartDto getCartByCustomerId(@PathVariable("customerId") int customerId) {
        return shoppingCartService.getCartByCustomerId(customerId);
    }

    @PostMapping(value = "/customers/products")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public int createNewShoppingCart(@Valid @RequestBody AddProductsInCartVm shoppingCartVm) {
        return shoppingCartService.createNewShoppingCart(shoppingCartVm);
    }

    @GetMapping(value = "/customers/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<ProductDto> getAllProductsInCartByCustomerId(@PathVariable("customerId") int customerId) {
        return shoppingCartService.getAllProductsInCartByCustomerId(customerId);
    }

    @GetMapping(value = "/customers/{customerId}/cartTotal")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getAllProductsPriceInCart(@PathVariable("customerId") int customerId) {
        return shoppingCartService.getAllProductsPriceInCart(customerId);
    }

}
