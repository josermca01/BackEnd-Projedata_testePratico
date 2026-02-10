package com.example.backend_projedata.service;

import java.util.*;

import com.example.backend_projedata.model.*;
import com.example.backend_projedata.repository.ProductCompositionRepository;
import com.example.backend_projedata.repository.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend_projedata.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductCompositionRepository compositionRepository;
    @Autowired
    private RawMaterialRepository materialRepository;
    public List<ProductDTOGetAllResponse> getProducts(){
        List<ProductDTOGetAllResponse> products = new ArrayList<>();
        for (Product product : repository.getProducts()) {
            Set<RawMaterialCompositionDTO> materials = new HashSet<>();
            for (ProductComposition composition : product.getComposition())
                materials.add(new RawMaterialCompositionDTO(composition.getRaw_material().getName(),composition.getRaw_material().getStock_quantity(),composition.getQuantity_required()));
            products.add(new ProductDTOGetAllResponse(product.getName(),product.getValue(),materials));
        }

        return products;
    }
    public ProductDTO getProductById(Long id){
        Optional<Product> op = repository.findById(id);
        Product product = op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
        ProductDTO productDTO = new ProductDTO(product.getName(), product.getValue());
        return productDTO;
    }
    public Product postProduct(ProductDTOGetAllResponse dto){
        if (!repository.findAll().isEmpty()){
        for (Product product : repository.findAll()) {
            if(dto.name().equalsIgnoreCase(product.getName())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Name invalid or already in the system!");
            }
        }}
        try {
        Product product = new Product();
        product.setName(dto.name());
        product.setValue(dto.value());
        repository.save(product);
        SetProductComposition(dto.materials(),product);
        return product;
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }
    public void SetProductComposition(Set<RawMaterialCompositionDTO> materials,Product product){
        for (RawMaterialCompositionDTO matDTO: materials){
            ProductComposition composition = new ProductComposition();
            composition.setProduct(product);
            composition.setRaw_material(materialRepository.findByName(matDTO.name()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Composition not found")));
            composition.setQuantity_required(matDTO.quantity_required());
            compositionRepository.save(composition);
        }
    }
    public Set<ProductComposition> UpdateProductComposition(Set<RawMaterialCompositionDTO> materials,Product product){
        Set<ProductComposition> compositionSet = new HashSet<>();
        List<ProductComposition> composition = new ArrayList<>( product.getComposition().stream().toList());
        while (composition.size()<materials.size()) {
            composition.add(new ProductComposition());
        }
        while (composition.size()>materials.size()) {
            compositionRepository.delete(composition.removeLast());
        }
        int i=0;
        for (RawMaterialCompositionDTO matDTO: materials){
            RawMaterial material = materialRepository.findByName(matDTO.name()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Composition not found"));
            composition.get(i).setProduct(product);
            composition.get(i).setRaw_material(material);
            composition.get(i).setQuantity_required(matDTO.quantity_required());
            compositionRepository.save(composition.get(i));
            compositionSet.add(composition.get(i));
            i++;
        }
        return compositionSet;
    }
    public ProductDTO update(Long id, ProductDTOGetAllResponse update) {
        Optional<Product> op = repository.findById(id);
        Product entity = op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
        for (Product product : repository.findAll()) {
            if(update.name().equalsIgnoreCase(product.getName())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Name invalid or already in the system!");
            }
        }
        entity.setComposition(UpdateProductComposition(update.materials(),entity));
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
