package com.example.demo.controller;

import com.example.demo.controller.model.DumbResponse;
import com.example.demo.controller.model.JobsResponse;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @PostMapping(value = "/seed")
    public ResponseEntity<Void> seedWithTonsOfData() throws InterruptedException {
        var jobId = UUID.randomUUID().toString();
        var clientId = UUID.randomUUID().toString();
        System.out.println("JOB ID: " + jobId);
        System.out.println("CLIENT ID: " + clientId);
        for (int i = 0; i < 5000; i++) {
            var p = new Product(null, clientId, jobId, String.valueOf(i), generateData(200, 10));
            productRepository.save(p);
            if (i % 1000 == 0) {
                System.out.println("I DID ANOTHER 1000... " + i);
            }
        }
        return ResponseEntity.ok().build();
    }

    private Map<String, String> generateData(int entries, int uuidEntries) {
        Map<String,String> data = new HashMap<>();
        for(int i = 0; i < entries; i++) {
            data.put(String.valueOf(i), generateData(uuidEntries));
        }
        return data;
    }

    private String generateData(int length) {
        StringBuilder b = new StringBuilder();
        for(int i = 0; i < length; i++) {
            b.append(UUID.randomUUID().toString());
        }
        return b.toString();
    }

    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<DumbResponse> getProduct(@PathVariable(value = "productId") String productId) {
        var example = Product.exampleForProductId(productId);
        var results = new ArrayList<Product>();
        productRepository.findAll(example).forEach(results::add);
        var findById = productRepository.findById(productId).orElse(null);
        return ResponseEntity.ok(new DumbResponse(results, findById));
    }

    @GetMapping(value = "/job/{jobId}")
    public ResponseEntity<Long> getProductsInJob(@PathVariable(value = "jobId") String jobId) {
        var example = Product.exampleForJobId(jobId);
        var res = StreamSupport.stream(productRepository.findAll(example).spliterator(), false)
                .count();
        return ResponseEntity.ok(res);
    }
}
