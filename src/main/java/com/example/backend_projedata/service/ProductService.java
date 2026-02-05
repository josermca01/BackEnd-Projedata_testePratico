package com.example.backend_projedata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_projedata.model.Product;
import com.example.backend_projedata.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    public List<Product> getProducts(){
        List<Product> products = repository.getProducts();
        // for (Perfil perfil2 : perfil) {
        //     perfil2.setFoto(ImageUtils.decompressImage(perfil2.getFoto()));
        // }
        return products;
    }
}
