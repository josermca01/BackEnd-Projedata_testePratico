package com.example.backend_projedata.controller;

import java.net.URI;
import java.util.List;

import com.example.backend_projedata.model.ProductDTOGetAllResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.backend_projedata.model.Product;
import com.example.backend_projedata.model.ProductDTO;
import com.example.backend_projedata.service.ProductService;


@CrossOrigin("*")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTOGetAllResponse>> getProducts(){
        List <ProductDTOGetAllResponse> list= productService.getProducts();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        ProductDTO dto = productService.getProductById(id);
        return ResponseEntity.ok().body(dto);
    }
    
    @PostMapping
    public ResponseEntity<Product> postMethodName(@RequestBody ProductDTOGetAllResponse productDTO) {
        //TODO: process POST request
        Product product = productService.postProduct(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(product);
    }
    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTOGetAllResponse update, @PathVariable Long id) {
        //TODO: process PUT request
        ProductDTO dto = productService.update(id,update);
        return ResponseEntity.ok().body(dto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
