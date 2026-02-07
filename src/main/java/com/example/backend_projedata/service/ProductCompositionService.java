package com.example.backend_projedata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend_projedata.model.Product;
import com.example.backend_projedata.model.ProductComposition;
import com.example.backend_projedata.model.ProductCompositionDTO;
import com.example.backend_projedata.model.ProductCompositionResponseDTO;
import com.example.backend_projedata.model.RawMaterial;
import com.example.backend_projedata.repository.ProductCompositionRepository;
import com.example.backend_projedata.repository.ProductRepository;
import com.example.backend_projedata.repository.RawMaterialRepository;

@Service
@Transactional
public class ProductCompositionService {
    @Autowired
    private ProductCompositionRepository repository;
    @Autowired 
    private ProductRepository productRepository;
    @Autowired 
    private RawMaterialRepository rawMaterialRepository;

    public List<ProductComposition> getProductCompositions(){
        List<ProductComposition> materials = repository.getProductCompositions();
        return materials;
    }
    public ProductCompositionDTO getCompositionById(Long id){
        Optional<ProductComposition> op = repository.findById(id);
        ProductComposition composition = op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Composition not found"));
        ProductCompositionDTO compositionDTO = new ProductCompositionDTO(composition.getProduct(), composition.getRaw_material(),composition.getQuantity_required());
        return compositionDTO;
    }
    public ProductComposition postComposition(ProductCompositionResponseDTO componentes){
        try{
        List<Product> product = productRepository.findByName(componentes.product());
        List<RawMaterial> rawmaterial = rawMaterialRepository.findByName(componentes.rawMaterial());
        for (ProductComposition composition : repository.findAll()) {
            if(product.getFirst().equals(composition.getProduct()) && rawmaterial.getFirst().equals(composition.getRaw_material())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "already in the system!");
            }
        }
        ProductComposition entity = new ProductComposition();
        entity.setProduct(product.getFirst());
        entity.setRaw_material(rawmaterial.getFirst());
        entity.setQuantity_required(componentes.quantity_required());
        return repository.save(entity);}
        catch (Exception e) {
            // TODO: handle exception
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error creating object");
        }
    }
    public ProductComposition update(ProductCompositionResponseDTO componentes,Long id){
        try{
        List<Product> product = productRepository.findByName(componentes.product());
        List<RawMaterial> rawmaterial = rawMaterialRepository.findByName(componentes.rawMaterial());
        ProductComposition entity = repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Composition not found"));
        for (ProductComposition composition : repository.findAll()) {
            if(product.getFirst().equals(composition.getProduct()) && rawmaterial.getFirst().equals(composition.getRaw_material())&&componentes.quantity_required()==composition.getQuantity_required()){
                repository.delete(entity);
                return entity;
            }
        }
        entity.setProduct(product.getFirst());
        entity.setRaw_material(rawmaterial.getFirst());
        entity.setQuantity_required(componentes.quantity_required());
        return repository.save(entity);
    }
        catch (Exception e) {
            // TODO: handle exception
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error creating object");
        }
    }
}
