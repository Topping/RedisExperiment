package com.example.demo.controller.model;

import com.example.demo.model.Product;

import java.util.List;

public class DumbResponse {
    private final List<Product> allExamples;
    private final Product findById;

    public DumbResponse(List<Product> allExamples, Product findById) {
        this.allExamples = allExamples;
        this.findById = findById;
    }

    public List<Product> getAllExamples() {
        return allExamples;
    }

    public Product getFindById() {
        return findById;
    }
}
