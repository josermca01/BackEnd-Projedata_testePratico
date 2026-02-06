package com.example.backend_projedata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.backend_projedata.model.RawMaterial;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial,Long>{
    @Query("SELECT r FROM Raw_Materials r ORDER BY r.id ASC")
    List<RawMaterial> getRawMaterials();
}
