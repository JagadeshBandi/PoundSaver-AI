package com.poundsaver.product.repository;

import com.poundsaver.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByRetailer(String retailer);

    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.normalizedName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchProducts(@Param("query") String query);

    @Query("SELECT p FROM Product p WHERE p.normalizedName = :normalizedName")
    List<Product> findByNormalizedName(@Param("normalizedName") String normalizedName);

    @Query("SELECT p FROM Product p WHERE p.inStock = true AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.normalizedName) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Product> findInStockProducts(@Param("query") String query);
}
