package com.sametkagankeskin.ecommerce.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sametkagankeskin.ecommerce.model.entity.CartProducts;
import com.sametkagankeskin.ecommerce.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "select * from siparisler s where s.fk_musteri_id = ?1", nativeQuery = true)
    List<Order> getOrderByCustomerId(int customerId);

    @Query(value = "select * from siparisler where siparis_tarihi < :startDate and fk_musteri_id = :customerId", nativeQuery = true)
    List<Order> findByOrderDateBefore(@Param("startDate") Date startDate, @Param("customerId") int customerId);

    @Query(value = "select * from siparisler where siparis_tarihi > :startDate and fk_musteri_id = :customerId", nativeQuery = true)
    List<Order> findByOrderDateAfter(@Param("startDate") Date startDate, @Param("customerId") int customerId);

    @Query(value = "select su.* from sepet_urunler su, siparisler s, sepetler s2 where s.siparis_id = :orderId and su.cart_sepet_id = s.fk_sepet_id and s2.sepet_id = s.fk_sepet_id", nativeQuery = true)
    List<CartProducts> getOrderDetailsById(@Param("orderId") int orderId);

    @Query(value = "select su.* from sepetler s, sepet_urunler su, siparisler s2  where su.cart_sepet_id  = s.sepet_id and s2.fk_sepet_id = s.sepet_id and s.aktif = false and s2.siparis_id = :orderId", nativeQuery = true)
    List<CartProducts> getCartProductsByOrderId(@Param("orderId") int orderId);
}
