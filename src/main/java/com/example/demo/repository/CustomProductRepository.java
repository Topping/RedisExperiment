package com.example.demo.repository;

import java.util.List;

public interface CustomProductRepository {
    List<String> listKeysForJob(String jobId);
}
