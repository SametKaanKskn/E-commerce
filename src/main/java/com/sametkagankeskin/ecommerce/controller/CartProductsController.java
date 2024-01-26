package com.sametkagankeskin.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sametkagankeskin.ecommerce.model.entity.CartProducts;
import com.sametkagankeskin.ecommerce.service.CartProductsService;

@RestController
@RequestMapping("/api/cartproducts/")
public class CartProductsController {

    private CartProductsService cartProductsService;

    public CartProductsController(CartProductsService cartProductsService) {
        this.cartProductsService = cartProductsService;
    }

    @GetMapping(value = "")
    public List<CartProducts> getAllCartProducts(@RequestParam String param) {
        return cartProductsService.getAllCartProducts();
    }

    @PostMapping(value = "{cartId}/{productId}/{productQuantity}")
    public int addProductInCart(@PathVariable("cartId") int cartId,
            @PathVariable("productId") int productId, @PathVariable("productQuantity") int productQuantity) {
        return cartProductsService.addProductInCart(cartId, productId, productQuantity);
    }

    @GetMapping(value = "{cartId}/{productId}")
    public int getProductQuantityByCartId(@PathVariable("cartId") int cartId,
            @PathVariable("productId") int productId) {
        return cartProductsService.getProductQuantityByCartId(cartId, productId);
    }

}
