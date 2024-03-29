package com.sametkagankeskin.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sametkagankeskin.ecommerce.exception.StockAmountNotEnoughException;
import com.sametkagankeskin.ecommerce.exception.customer.CustomerNotFoundException;
import com.sametkagankeskin.ecommerce.exception.product.ProductNotFoundException;
import com.sametkagankeskin.ecommerce.exception.shoppingCart.ShoppingCartNotFoundException;
import com.sametkagankeskin.ecommerce.model.dto.ProductDto;
import com.sametkagankeskin.ecommerce.model.dto.ShoppingCartDto;
import com.sametkagankeskin.ecommerce.model.entity.CartProducts;
import com.sametkagankeskin.ecommerce.model.entity.Customer;
import com.sametkagankeskin.ecommerce.model.entity.Product;
import com.sametkagankeskin.ecommerce.model.entity.ShoppingCart;
import com.sametkagankeskin.ecommerce.model.vm.AddProductsInCartVm;
import com.sametkagankeskin.ecommerce.repository.CartProductsRepository;
import com.sametkagankeskin.ecommerce.repository.CustomerRepository;
import com.sametkagankeskin.ecommerce.repository.ProductRepository;
import com.sametkagankeskin.ecommerce.repository.ShoppingCartRepository;
import com.sametkagankeskin.ecommerce.service.CartProductsService;
import com.sametkagankeskin.ecommerce.service.ShoppingCartService;
import com.sametkagankeskin.ecommerce.util.mapper.ModelMapperManager;

import jakarta.transaction.Transactional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;
    private CustomerRepository customerRepository;
    private CartProductsService cartProductsService;
    private ProductRepository productRepository;
    private CartProductsRepository cartProductsRepository;
    private ModelMapperManager modelMapperManager;
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ModelMapperManager modelMapperManager,
            ProductRepository productRepository, CustomerRepository customerRepository,
            CartProductsRepository cartProductsRepository, CartProductsService cartProductsService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.modelMapperManager = modelMapperManager;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.cartProductsRepository = cartProductsRepository;
        this.cartProductsService = cartProductsService;
    }

    @Override
    public List<ShoppingCartDto> getAllCarts() {
        Authentication auth = getAuth();
        String message = auth.getName() + " isimli admin tüm sepetleri görüntüledi!";
        logger.info(message);
        return shoppingCartRepository.findAll().stream()
                .map(cart -> modelMapperManager.forResponse().map(cart, ShoppingCartDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingCartDto getCartByCartId(int cartId) {
        return modelMapperManager.forResponse().map(shoppingCartRepository.findById(cartId).get(),
                ShoppingCartDto.class);
    }

    @Override
    public ShoppingCartDto getCartByCustomerId(int customerId) {
        Authentication auth = getAuth();
        if (!auth.getName().equals(shoppingCartRepository.getCustomerNameById(customerId))) {
            logger.error("Bu sepet kaydı " + auth.getName() + " müşterisine ait değildir!");
            throw new AccessDeniedException("Kullanıcı sadece kendi verilerine erişebilir!");
        }
        return modelMapperManager.forResponse().map(shoppingCartRepository.getShoppingCartByCustomerId(customerId),
                ShoppingCartDto.class);
    }

    // Bunu bir de List<AddShoppingCartVm> olarak düzenle. İçlerinden birisi stok
    // durumunu aşsın bu yüzden ondan
    // önce eklenenler de Transaction ile Rollback yaparak database e kaydedilmeli
    @Override
    @Transactional
    public int createNewShoppingCart(AddProductsInCartVm cartVm) {

        Authentication auth = getAuth();
        if (!auth.getName().equals(shoppingCartRepository.getCustomerNameById(cartVm.getCustomerId()))) {
            logger.error("Bu sepet kaydı " + auth.getName() + " müşterisine ait değildir!");
            throw new AccessDeniedException("Kullanıcı sadece kendi verilerine erişebilir!");
        }

        Product product = productRepository.findById(cartVm.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found")); /////// YENİ

        if (product.getStockAmount() < cartVm.getQuantity()) { // YENİ
            String warning = "Bu üründen istenilen miktarda stokta bulunmamaktadır!";
            logger.error(warning);
            throw new StockAmountNotEnoughException(warning);
        }

        if (shoppingCartRepository.hasCustomerAvailableCart(cartVm.getCustomerId())) {
            logger.info("Var olan sepetinize ürün eklenecektir!");
            ShoppingCart cart = shoppingCartRepository.getShoppingCartByCustomerId(cartVm.getCustomerId());
            if (shoppingCartRepository.checkProductIfExists(product.getProductId(), cart.getCartId())) {
                logger.info("Bu sepette bu üründen vardı! Şimdi daha fazlasını eklemektesiniz!");
                int prevQuantity = cartProductsService.getProductQuantityByCartId(cart.getCartId(),
                        product.getProductId());
                int newQuantity = prevQuantity + cartVm.getQuantity();
                cart.setQuantity(cart.getQuantity() + cartVm.getQuantity());
                cart.setTotalAmount(cart.getTotalAmount() + (product.getProductPrice() * cartVm.getQuantity()));
                shoppingCartRepository.save(cart);
                cartProductsRepository.updateProductQuantityInCart(cart.getCartId(), product.getProductId(),
                        newQuantity);
                return cart.getCartId();
            }
            cart.setQuantity(cart.getQuantity() + cartVm.getQuantity());
            cart.setTotalAmount(cart.getTotalAmount() + (product.getProductPrice() * cartVm.getQuantity()));
            shoppingCartRepository.save(cart);
            shoppingCartRepository.saveShoppingCartAfterAdding(cart.getCartId(), cartVm.getProductId(),
                    cartVm.getQuantity());
            // logger.info(auth.getName() + " isimli kullanıcı ürünleri başarıyla sepetine
            // ekledi!");
            return cart.getCartId();
        }
        ShoppingCartDto cartDto = modelMapperManager.forResponse().map(cartVm, ShoppingCartDto.class);
        cartDto.setTotalAmount(product.getProductPrice() * cartDto.getQuantity());
        ShoppingCart newCart = modelMapperManager.forResponse().map(cartDto, ShoppingCart.class);
        newCart.setSoftDeleted(true);
        newCart.setQuantity(cartVm.getQuantity());
        shoppingCartRepository.save(newCart);
        CartProducts cartProducts = new CartProducts(newCart, product, cartVm.getQuantity());
        cartProductsRepository.save(cartProducts);
        logger.info("Yeni sepetiniz oluşturuldu ve ürünler eklendi!");
        return newCart.getCartId();
    }

    @Override
    public String deleteCartById(int cartId) {
        shoppingCartRepository.deleteById(cartId);
        return "Kayıt silindi!";
    }

    // BURALAR DUZELTİLDİ DIKKAT
    @Override
    public List<ProductDto> getAllProductsInCartByCustomerId(int customerId) {
        Authentication auth = getAuth();
        if (!auth.getName().equals(customerRepository.findById(customerId).get().getUsername())) { // ZATEN CUSTOMER
                                                                                                   // REPOSİTORYDE VAR
                                                                                                   // AYRI QUERY
                                                                                                   // YAZILMASI GEREKLİ
                                                                                                   // DEĞİL
            logger.error("Bu sepet kaydı " + auth.getName() + " müşterisine ait değildir!");
            throw new AccessDeniedException("Kullanıcı sadece kendi verilerine erişebilir!");
        }

        List<CartProducts> cartProducts = cartProductsRepository.getCartProductsByCustomerId(customerId);
        List<Product> products = new ArrayList<>();
        for (CartProducts cartProducts2 : cartProducts) {
            products.add(productRepository.findById(cartProducts2.getProduct().getProductId()).get());
        }
        return products.stream()
                .map(product -> modelMapperManager.forResponse().map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getAllProductsPriceInCart(int customerId) {
        Authentication auth = getAuth();
        if (!auth.getName().equals(shoppingCartRepository.getCustomerNameById(customerId))) { // ZATEN CUSTOMER
                                                                                              // REPOSİTORYDE VAR AYRI
                                                                                              // QUERY YAZILMASI GEREKLİ
                                                                                              // DEĞİL
            logger.error("Bu sepet kaydı " + auth.getName() + " müşterisine ait değildir!");
            throw new AccessDeniedException("Kullanıcı sadece kendi verilerine erişebilir!");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
        if (!shoppingCartRepository.hasCustomerAvailableCart(customerId)) {
            throw new ShoppingCartNotFoundException("Sepetinizde Ürün bulunmamaktadır.");
        }
        double total = 0;
        List<CartProducts> cartProductsByCustomerId = cartProductsRepository.getCartProductsByCustomerId(customerId);
        for (CartProducts cartProducts : cartProductsByCustomerId) {
            total += cartProducts.getProductQuantity() * (cartProducts.getProduct().getProductPrice());
        }
        return "Sayın " + customer.getCustomerFirstName() + " " + customer.getCustomerLastName()
                + " toplam sepet tutarınız " + total + "Tl dir.";
    }

    public Authentication getAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth;
    }

}
