package com.yelyzaveta.costume_recommender_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity that represents a theme of a costume.
 * Examples: vampires, witches, zombies, fantasy, retro, etc.
 * A theme can be associated with multiple costumes.
 */
@Data
@Entity
@EqualsAndHashCode(exclude = "costumes")
@ToString(exclude = "costumes")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "themes")
    private Set<Costume> costumes = new HashSet<>();
}
