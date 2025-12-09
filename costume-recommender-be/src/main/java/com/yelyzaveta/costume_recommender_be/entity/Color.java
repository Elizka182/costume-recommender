package com.yelyzaveta.costume_recommender_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a color for costumes.
 * A costume can have multiple colors, and a color can be used in multiple costumes.
 */
@Data
@Entity
@EqualsAndHashCode(exclude = "costumes")
@ToString(exclude = "costumes")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "colors")
    private Set<Costume> costumes = new HashSet<>();
}
