package com.example.backend_projedata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend_projedata.model.Product;
import com.example.backend_projedata.model.ProductDTO;
import com.example.backend_projedata.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    public List<Product> getProducts(){
        List<Product> products = repository.getProducts();
        return products;
    }
    public ProductDTO getProductById(Long id){
        Optional<Product> op = repository.findById(id);
        Product product = op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
        ProductDTO productDTO = new ProductDTO(product.getName(), product.getValue());
        return productDTO;
    }
    public Product postProduct(ProductDTO dto){
        for (Product product : repository.findAll()) {
            if(dto.name().equalsIgnoreCase(product.getName())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Name invalid or already in the system!");
            }
        }
        try {
        Product product = new Product();
        product.setName(dto.name());
        product.setValue(dto.value());
        return repository.save(product);
         
            
        } catch (Exception e) {
            // TODO: handle exception
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Erro na criacao do perfil");
        }
    }
    public ProductDTO update(Long id, ProductDTO update) {
        Optional<Product> op = repository.findById(id);
        Product entity = op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
        for (Product product : repository.findAll()) {
            if(update.name().equalsIgnoreCase(product.getName())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Name invalid or already in the system!");
            }
        }
        entity.setName(update.name());
        entity.setValue(update.value());
        entity = repository.save(entity);
        return new ProductDTO(entity.getName(),entity.getValue());
        
    }
    public void delete(Long id){
        Optional<Product> op = repository.findById(id);
        op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
        repository.deleteById(id);
    }
}
