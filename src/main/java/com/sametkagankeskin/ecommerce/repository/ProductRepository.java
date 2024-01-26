package com.sametkagankeskin.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sametkagankeskin.ecommerce.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByProductNameIgnoreCase(String productName);

    @Query(nativeQuery = true, value = "select * from urunler u join kategoriler k on u.fk_kategori_id = k.kategori_id where  LOWER(k.kategori_adi) LIKE LOWER(CONCAT('%', :categoryName, '%'))")
    List<Product> getProductsByCategoryName(@Param("categoryName") String categoryName);

    @Query(nativeQuery = true, value = "select * from urunler where urun_fiyati > :productPrice")
    List<Product> getProductsWhosePriceBigger(@Param("productPrice") double productPrice);

    @Query(nativeQuery = true, value = "select * from urunler where urun_fiyati < :productPrice")
    List<Product> getProductsWhosePriceLower(@Param("productPrice") double productPrice);

}