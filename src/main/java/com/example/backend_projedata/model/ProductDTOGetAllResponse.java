package com.example.backend_projedata.model;

import java.util.Set;

public record ProductDTOGetAllResponse(String name, double value, Set<RawMaterialCompositionDTO> materials) {
}
