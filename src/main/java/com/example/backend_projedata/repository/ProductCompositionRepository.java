package com.example.backend_projedata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend_projedata.model.ProductComposition;

@Repository
public interface ProductCompositionRepository extends JpaRepository<ProductComposition,Long>{
    @Query("SELECT p FROM Product_Composition p ORDER BY p.id ASC")
    List<ProductComposition> getProductCompositions();
    @Query("""
       SELECT COUNT(pc)
       FROM Product_Composition pc
       WHERE pc.product.id = :productId
       """)
    Long CountByProductId(@Param("productId") Long productId);
    @Query("""
       SELECT pc
       FROM Product_Composition pc
       WHERE pc.product.id = :productId
       """)
    List<ProductComposition> findByProductId(@Param("productId") Long productId);
}
