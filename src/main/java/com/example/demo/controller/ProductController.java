package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping(value = "/product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product p = productRepository.save(product);
        return ResponseEntity.ok(p);
    }

    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "productId") String productId) {
        var example = Product.exampleForProductId(productId);
        var res = productRepository.findOne(example);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
