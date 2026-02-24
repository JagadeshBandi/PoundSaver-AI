package com.poundsaver.price.repository;

import com.poundsaver.price.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, String> {

    List<PriceHistory> findByProductIdOrderByTimestampDesc(String productId);

    @Query("SELECT ph FROM PriceHistory ph WHERE ph.productId = :productId " +
           "AND ph.timestamp >= :startDate ORDER BY ph.timestamp DESC")
    List<PriceHistory> findByProductIdAndDateRange(
            @Param("productId") String productId,
            @Param("startDate") LocalDateTime startDate);

    @Query("SELECT ph FROM PriceHistory ph WHERE ph.retailer = :retailer " +
           "AND ph.timestamp >= :startDate ORDER BY ph.timestamp DESC")
    List<PriceHistory> findByRetailerAndDateRange(
            @Param("retailer") String retailer,
            @Param("startDate") LocalDateTime startDate);
}
