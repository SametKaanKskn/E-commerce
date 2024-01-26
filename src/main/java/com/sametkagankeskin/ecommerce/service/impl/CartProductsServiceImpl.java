package com.sametkagankeskin.ecommerce.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sametkagankeskin.ecommerce.model.entity.CartProducts;
import com.sametkagankeskin.ecommerce.model.entity.Product;
import com.sametkagankeskin.ecommerce.model.entity.ShoppingCart;
import com.sametkagankeskin.ecommerce.repository.CartProductsRepository;
import com.sametkagankeskin.ecommerce.repository.ProductRepository;
import com.sametkagankeskin.ecommerce.repository.ShoppingCartRepository;
import com.sametkagankeskin.ecommerce.service.CartProductsService;

@Service
public class CartProductsServiceImpl implements CartProductsService {

    private CartProductsRepository cartProductsRepository;
    private ShoppingCartRepository shoppingCartRepository;
    private ProductRepository productRepository;

    public CartProductsServiceImpl(CartProductsRepository cartProductsRepository,
            ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository) {
        this.cartProductsRepository = cartProductsRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartProducts> getAllCartProducts() {
        return cartProductsRepository.findAll();
    }

    @Override
    public int addProductInCart(int cartId, int productId, int productQuantity) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId).get();
        Product product = productRepository.findById(productId).get();
        CartProducts cartProducts = new CartProducts(cart, product, productQuantity);
        cartProductsRepository.save(cartProducts);
        return 1;
    }

    @Override
    public int getProductQuantityByCartId(int cartId, int productId) {
        CartProducts cartProducts = cartProductsRepository.findCartProductByCartAndProductId(cartId, productId);
        return cartProducts.getProductQuantity();
    }

}
