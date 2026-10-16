package com.example.productservice.controller;

import com.example.productservice.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(1L, "Laptop Dell XPS 15", 1500.0, 10),
            new Product(2L, "iPhone 15 Pro", 1100.0, 25),
            new Product(3L, "Sony WH-1000XM5 Headphone", 350.0, 50)
    ));

    private final AtomicLong idGenerator = new AtomicLong(4L);

    @GetMapping
    public List<Product> getAllProducts() {
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product.setId(idGenerator.getAndIncrement());
        products.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
