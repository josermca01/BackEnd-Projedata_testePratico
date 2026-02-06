package com.example.backend_projedata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.backend_projedata.model.ProductComposition;

@Repository
public interface ProductCompositionRepository extends JpaRepository<ProductComposition,Long>{
    @Query("SELECT p FROM Product_Composition p ORDER BY p.id ASC")
    List<ProductComposition> getProductCompositions();
}
