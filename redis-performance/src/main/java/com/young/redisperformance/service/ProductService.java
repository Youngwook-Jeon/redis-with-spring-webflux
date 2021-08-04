package com.young.redisperformance.service;

import com.young.redisperformance.entity.Product;
import com.young.redisperformance.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Mono<Product> getProduct(int id) {
        return this.productRepository.findById(id);
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        return this.productRepository.findById(id)
                .flatMap(p -> productMono.doOnNext(pr -> pr.setId(id)))
                .flatMap(this.productRepository::save);
    }
}
