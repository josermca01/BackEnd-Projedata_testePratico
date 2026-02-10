package com.example.backend_projedata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend_projedata.model.RawMaterial;
import com.example.backend_projedata.model.RawMaterialDTO;
import com.example.backend_projedata.repository.RawMaterialRepository;

@Service
@Transactional
public class RawMaterialService {
    @Autowired
    private RawMaterialRepository repository;
    public List<RawMaterial> getRawMaterials(){
        List<RawMaterial> materials = repository.getRawMaterials();
        return materials;
    }
    public RawMaterialDTO getRawMaterialById(Long id){
        Optional<RawMaterial> op = repository.findById(id);
        RawMaterial material = op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Material not found"));
        RawMaterialDTO materialDTO = new RawMaterialDTO(material.getName(), material.getStock_quantity());
        return materialDTO;
    }
    public RawMaterial postRawMaterial(RawMaterialDTO dto){
        if (!repository.findAll().isEmpty()){
        for (RawMaterial material : repository.findAll()) {
            if(dto.name().equalsIgnoreCase(material.getName())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Name invalid or already in the system!");
            }
        }}
        try {
        RawMaterial material = new RawMaterial();
        material.setName(dto.name());
        material.setStock_quantity(dto.stock_quantity());
        return repository.save(material);
         
            
        } catch (Exception e) {
            // TODO: handle exception
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error creating object");
        }
    }
    public RawMaterialDTO update(Long id, RawMaterialDTO update) {
        Optional<RawMaterial> op = repository.findById(id);
        RawMaterial entity = op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"RawMaterial not found"));
        for (RawMaterial material : repository.findAll()) {
            if(update.name().equalsIgnoreCase(material.getName())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Name invalid or already in the system!");
            }
        }
        entity.setName(update.name());
        entity.setStock_quantity(update.stock_quantity());
        entity = repository.save(entity);
        return new RawMaterialDTO(entity.getName(),entity.getStock_quantity());
        
    }
    public void delete(Long id){
        Optional<RawMaterial> op = repository.findById(id);
        op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"RawMaterial not found"));
        repository.deleteById(id);
    }
}
