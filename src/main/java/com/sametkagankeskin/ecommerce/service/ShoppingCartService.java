package com.sametkagankeskin.ecommerce.service;

import java.util.List;

import com.sametkagankeskin.ecommerce.model.dto.ProductDto;
import com.sametkagankeskin.ecommerce.model.dto.ShoppingCartDto;
import com.sametkagankeskin.ecommerce.model.vm.AddProductsInCartVm;

public interface ShoppingCartService {
    List<ShoppingCartDto> getAllCarts();

    ShoppingCartDto getCartByCartId(int cartId);

    ShoppingCartDto getCartByCustomerId(int customerId);

    int createNewShoppingCart(AddProductsInCartVm cartVm);

    String deleteCartById(int cartId);

    List<ProductDto> getAllProductsInCartByCustomerId(int customerId);

    String getAllProductsPriceInCart(int customerId);
}
