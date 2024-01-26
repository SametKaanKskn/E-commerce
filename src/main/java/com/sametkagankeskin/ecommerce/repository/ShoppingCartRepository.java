package com.sametkagankeskin.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sametkagankeskin.ecommerce.model.entity.Product;
import com.sametkagankeskin.ecommerce.model.entity.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    @Query(value = "select * from sepetler s where s.fk_musteri_id = ?1 and s.aktif = true", nativeQuery = true)
    ShoppingCart getShoppingCartByCustomerId(int customerId);

    @Modifying
    @Query(value = "insert into sepet_urunler(sepet_id, urun_id, cart_sepet_id, product_urun_id, urun_miktari) values(:cartId, :productId, :cartId, :productId, :productQuantity)", nativeQuery = true)
    void saveShoppingCartAfterAdding(@Param("cartId") int cartId, @Param("productId") int productId,
            @Param("productQuantity") int productQuantity);
    // @Query(value = "select * from sepetler s where s.fk_musteri_id = ?1",
    // nativeQuery = true)
    // ShoppingCart getCartByCustomerId(int customerId);

    @Query(value = "select exists(select * from sepet_urunler su where su.cart_sepet_id = :cartId and su.product_urun_id in (:productId))", nativeQuery = true)
    boolean checkProductIfExists(@Param("productId") int productId, @Param("cartId") int cartId);

    @Query(value = "select m.kullanici_adi from musteriler m where m.musteri_id = ?1", nativeQuery = true)
    String getCustomerNameById(int customerId);

    @Modifying
    @Query(value = "UPDATE sepetler SET aktif = false WHERE fk_musteri_id = ?1", nativeQuery = true)
    void updateCartAfterOrder(@Param("customerId") int customerId);

    @Query(value = "select exists(select * from sepetler s where s.aktif = true and s.fk_musteri_id = ?1)", nativeQuery = true)
    boolean hasCustomerAvailableCart(@Param("customerId") int customerId);

    @Query(value = "select u.* from urunler u join sepet_urunler su on su.cartId = :cartId", nativeQuery = true)
    List<Product> getAllProductsInCart(@Param("cartId") int cartId);

}
