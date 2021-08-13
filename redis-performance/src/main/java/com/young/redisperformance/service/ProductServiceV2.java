package com.young.redisperformance.service;

import com.young.redisperformance.entity.Product;
import com.young.redisperformance.repository.ProductRepository;
import com.young.redisperformance.service.util.CacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceV2 {

    @Autowired
    private CacheTemplate<Integer, Product> cacheTemplate;

    @Autowired
    private ProductVisitService visitService;

    public Mono<Product> getProduct(int id) {
        return this.cacheTemplate.get(id)
                .doFirst(() -> this.visitService.addVisit(id));
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        return productMono
                .flatMap((p -> this.cacheTemplate.update(id, p)));
    }

    public Mono<Void> deleteProduct(int id) {
        return this.cacheTemplate.delete(id);
    }
}
