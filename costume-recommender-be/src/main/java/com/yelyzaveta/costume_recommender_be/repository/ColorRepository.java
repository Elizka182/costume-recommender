package com.yelyzaveta.costume_recommender_be.repository;

import com.yelyzaveta.costume_recommender_be.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Color} entities.
 * Provides CRUD operations inherited from {@link JpaRepository}
 * and additional query methods.
*/
 public interface ColorRepository extends JpaRepository<Color, Integer> {

    /**
     * Finds a Color entity by its name.
     *
     * @param name the name of the color to search for
     * @return the Color entity with the given name, or null if none found
     */
    Color findByName(String name);
}
