package com.example.backend_projedata.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.backend_projedata.model.RawMaterial;
import com.example.backend_projedata.model.RawMaterialDTO;
import com.example.backend_projedata.service.RawMaterialService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rawmaterials")
public class RawMaterialController {
    @Autowired
    private RawMaterialService rawMaterialService;

    @GetMapping
    public ResponseEntity<List<RawMaterial>> getRawMaterials(){
        List <RawMaterial> list= rawMaterialService.getRawMaterials();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("{id}")
    public ResponseEntity<RawMaterialDTO> getRawMaterial(@PathVariable Long id) {
        RawMaterialDTO dto = rawMaterialService.getRawMaterialById(id);
        return ResponseEntity.ok().body(dto);
    }
    
    @PostMapping
    public ResponseEntity<RawMaterial> postMethodName(@ModelAttribute RawMaterialDTO rawMaterialDTO) {
        //TODO: process POST request
        RawMaterial rawMaterial = rawMaterialService.postRawMaterial(rawMaterialDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rawMaterial.getId()).toUri();
        return ResponseEntity.created(uri).body(rawMaterial);
    }
    @PutMapping("{id}")
    public ResponseEntity<RawMaterialDTO> update(@ModelAttribute RawMaterialDTO update, @PathVariable Long id) {
        //TODO: process PUT request
        RawMaterialDTO dto = rawMaterialService.update(id,update);
        return ResponseEntity.ok().body(dto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        rawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
