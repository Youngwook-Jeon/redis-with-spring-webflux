package com.young.redisperformance.service;

import com.young.redisperformance.entity.Product;
import com.young.redisperformance.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;

    @Value("classpath:schema.sql")
    private Resource resource;

    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(query);

        Mono<Void> insert = Flux.range(1, 100)
                .map(i -> new Product(null, "product" + i, ThreadLocalRandom.current().nextInt(1, 100)))
                .collectList()
                .flatMapMany(l -> this.productRepository.saveAll(l))
                .then();

        this.entityTemplate.getDatabaseClient()
                .sql(query)
                .then()
                .then(insert)
                .doFinally(s -> System.out.println("데이터 셋업이 완료되었습니다. " + s))
                .subscribe();
    }
}
