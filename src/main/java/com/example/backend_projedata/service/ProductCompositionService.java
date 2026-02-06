package com.example.backend_projedata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_projedata.model.ProductComposition;
import com.example.backend_projedata.repository.ProductCompositionRepository;

@Service
public class ProductCompositionService {
    @Autowired
    private ProductCompositionRepository repository;
    public List<ProductComposition> getProductCompositions(){
        List<ProductComposition> materials = repository.getProductCompositions();
        return materials;
    }

}
