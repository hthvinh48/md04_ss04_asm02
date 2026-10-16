package com.example.customerservice.controller;

import com.example.customerservice.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer(1L, "Nguyen Van A", "vana@example.com", "0901234567"),
            new Customer(2L, "Tran Thi B", "thib@example.com", "0912345678"),
            new Customer(3L, "Le Van C", "vanc@example.com", "0987654321")
    ));

    private final AtomicLong idGenerator = new AtomicLong(4L);

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setId(idGenerator.getAndIncrement());
        customers.add(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
}
