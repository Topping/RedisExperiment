package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Duration;
import java.util.Map;

@RedisHash(value = Product.REDIS_HASH, timeToLive = 300) // TTL = 5 minutes (300 seconds)
public class Product {
    public static final String REDIS_HASH = "products";

    @Id
    private final String id;
    @Indexed
    private final String clientId;
    @Indexed
    private final String jobId;
    private final Map<String, String> data;

    public Product(String clientId, String id, String jobId, Map<String, String> data) {
        this.clientId = clientId;
        this.id = id;
        this.jobId = jobId;
        this.data = data;
    }

    public String getClientId() {
        return clientId;
    }

    public String getId() {
        return id;
    }

    public String getJobId() {
        return jobId;
    }

    public Map<String, String> getData() {
        return data;
    }

    public static Example<Product> exampleFor(String clientId, String productId, String jobId) {
        return Example.of(new Product(clientId, productId, jobId, null), ExampleMatcher.matchingAll());
    }

    public static Example<Product> exampleForProductId(String productId) {
        return Example.of(new Product(null, productId, null, null), ExampleMatcher.matchingAll());
    }
}
