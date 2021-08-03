package com.young.redisspring.city.service;

import com.young.redisspring.city.client.CityClient;
import com.young.redisspring.city.dto.City;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CityService {

    private RMapReactive<String, City> cityMap;

    @Autowired
    private CityClient cityClient;

    public CityService(RedissonReactiveClient client) {
        this.cityMap = client.getMap("city", new TypedJsonJacksonCodec(String.class, City.class));
    }

    public Mono<City> getCity(final String zipCode) {
        return this.cityMap.get(zipCode)
                .switchIfEmpty(
                        this.cityClient
                                .getCity(zipCode)
                                .flatMap(c ->
                                        this.cityMap.fastPut(zipCode, c).thenReturn(c))
                );
    }

    @Scheduled(fixedRate = 10_000)
    public void updateCity() {
        this.cityClient.getAll()
                .collectList()
                .map(list -> list.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
                .flatMap(this.cityMap::putAll)
                .subscribe();
    }
}
