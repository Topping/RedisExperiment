package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String>, QueryByExampleExecutor<Product> {
}
