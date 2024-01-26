package com.sametkagankeskin.ecommerce.service;

import java.util.List;

import com.sametkagankeskin.ecommerce.model.entity.CartProducts;

public interface CartProductsService {
    List<CartProducts> getAllCartProducts();

    int addProductInCart(int cartId, int productId, int productQuantity);

    int getProductQuantityByCartId(int cartId, int productId);
}
