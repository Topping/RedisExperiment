package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Map;

@RedisHash(value = Product.REDIS_HASH, timeToLive = 300) // TTL = 5 minutes (300 seconds)
public class Product {
    public static final String REDIS_HASH = "products";

    @Id
    private final String hashId;
    @Indexed
    private final String productId;
    @Indexed
    private final String clientId;
    @Indexed
    private final String jobId;
    private final Map<String, String> data;

    public Product(String hashId, String clientId, String jobId, String productId, Map<String, String> data) {
        this.hashId = hashId;
        this.clientId = clientId;
        this.jobId = jobId;
        this.productId = productId;
        this.data = data;
    }

    public String getHashId() {
        return hashId;
    }

    public String getProductId() {
        return productId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getJobId() {
        return jobId;
    }

    public Map<String, String> getData() {
        return data;
    }

    public static Example<Product> exampleFor(String clientId, String productId, String jobId) {
        return Example.of(new Product(null, clientId, jobId, productId, null), ExampleMatcher.matchingAll());
    }

    public static Example<Product> exampleForProductId(String productId) {
        return Example.of(new Product(null, null, null, productId, null), ExampleMatcher.matchingAll());
    }

    public static Example<Product> exampleForJobId(String jobId) {
        return Example.of(new Product(null, null, jobId, null, null), ExampleMatcher.matchingAll());
    }
}
