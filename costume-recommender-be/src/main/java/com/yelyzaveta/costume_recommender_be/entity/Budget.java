package com.yelyzaveta.costume_recommender_be.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity representing a budget category for costumes.
 * Defines the cost level of a costume (e.g., "low", "medium", "high").
 */
@Data
@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
