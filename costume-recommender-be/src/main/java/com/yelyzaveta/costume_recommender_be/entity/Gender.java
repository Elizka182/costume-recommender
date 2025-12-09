package com.yelyzaveta.costume_recommender_be.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity that represents gender presentation for a costume.
 * Examples: unisex, feminine, masculine.
 */
@Data
@Entity
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
