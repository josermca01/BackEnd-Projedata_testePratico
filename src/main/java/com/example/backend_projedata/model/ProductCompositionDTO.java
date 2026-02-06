package com.example.backend_projedata.model;

public record ProductCompositionDTO(Product product,RawMaterial rawMaterial,double quantity_required) {
}