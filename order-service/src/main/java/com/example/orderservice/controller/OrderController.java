package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final List<Order> orders = new ArrayList<>(Arrays.asList(
            new Order(1L, 1L, 1L, 1, "COMPLETED"),
            new Order(2L, 2L, 2L, 2, "PENDING"),
            new Order(3L, 1L, 3L, 1, "PROCESSING")
    ));

    private final AtomicLong idGenerator = new AtomicLong(4L);

    @GetMapping
    public List<Order> getAllOrders() {
        return orders;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        order.setId(idGenerator.getAndIncrement());
        if (order.getStatus() == null) {
            order.setStatus("PENDING");
        }
        orders.add(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
