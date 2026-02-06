package com.example.backend_projedata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_projedata.model.ProductComposition;
import com.example.backend_projedata.service.ProductCompositionService;

@RestController
@RequestMapping("/productcomposition")
public class ProductCompositionController {
    @Autowired
    private ProductCompositionService rawMaterialService;

    @GetMapping
    public ResponseEntity<List<ProductComposition>> getProductCompositions(){
        List <ProductComposition> list= rawMaterialService.getProductCompositions();
        return ResponseEntity.ok().body(list);
    }
}
