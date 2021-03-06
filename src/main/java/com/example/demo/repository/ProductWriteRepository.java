package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWriteRepository extends CrudRepository<Product, String> {
}
