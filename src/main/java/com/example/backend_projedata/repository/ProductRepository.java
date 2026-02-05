package com.example.backend_projedata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.backend_projedata.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    @Query("SELECT p FROM Products p ORDER BY p.id ASC")
    List<Product> getProducts();
}
