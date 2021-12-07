package com.example.demo.controller.model;

import com.example.demo.model.Product;

import java.util.List;

public class JobsResponse {
    private final List<Product> productsInJob;

    public JobsResponse(List<Product> productsInJob) {
        this.productsInJob = productsInJob;
    }

    public List<Product> getProductsInJob() {
        return productsInJob;
    }
}
