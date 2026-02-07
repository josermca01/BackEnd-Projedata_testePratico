package com.example.backend_projedata.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.backend_projedata.model.ProductComposition;
import com.example.backend_projedata.model.ProductCompositionDTO;
import com.example.backend_projedata.model.ProductCompositionResponseDTO;
import com.example.backend_projedata.service.ProductCompositionService;

@RestController
@RequestMapping("/productcomposition")
public class ProductCompositionController {
    @Autowired
    private ProductCompositionService service;

    @GetMapping
    public ResponseEntity<List<ProductComposition>> getProductCompositions(){
        List <ProductComposition> list= service.getProductCompositions();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("{id}")
    public ResponseEntity<ProductCompositionDTO> getProduct(@PathVariable Long id) {
        ProductCompositionDTO dto = service.getCompositionById(id);
        return ResponseEntity.ok().body(dto);
    }
    
    @PostMapping
    public ResponseEntity<ProductComposition> postMethodName(@ModelAttribute ProductCompositionResponseDTO compositionDTO) {
        //TODO: process POST request
        ProductComposition composition = service.postComposition(compositionDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(composition.getId()).toUri();
        return ResponseEntity.created(uri).body(composition);
    }
    @PutMapping("{id}")
    public ResponseEntity<ProductComposition> update(@PathVariable Long id,@ModelAttribute ProductCompositionResponseDTO compositionDTO) {
        //TODO: process PUT request
        ProductComposition composition = service.update(compositionDTO,id);
        return ResponseEntity.ok().body(composition);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ProductComposition> delete(@PathVariable Long id) {
        //TODO: process DELETE request
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
