package com.yelyzaveta.costume_recommender_be.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity that represents the scare level of a costume.
 * Used to describe how spooky, classic, funny, cute, sexy or dark a costume is.
 */
@Data
@Entity
public class ScareLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
