package com.young.redisperformance.controller;

import com.young.redisperformance.entity.Product;
import com.young.redisperformance.service.ProductServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductControllerV1 {

    @Autowired
    private ProductServiceV1 productServiceV1;

    @GetMapping("/{id}")
    public Mono<Product> getProduct(@PathVariable int id) {
        return this.productServiceV1.getProduct(id);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable int id, @RequestBody Mono<Product> productMono) {
        return this.productServiceV1.updateProduct(id, productMono);
    }
}
