package com.young.redisperformance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table // 레디스가 아닌, r2dbc와 연관된 엔티티 클래스
@Data @ToString
@AllArgsConstructor @NoArgsConstructor
public class Product {

    @Id
    private Integer id;

    private String description;

    private double price;
}
