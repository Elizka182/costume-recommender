package com.yelyzaveta.costume_recommender_be.repository;

import com.yelyzaveta.costume_recommender_be.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link Theme} entities.
*/
 public interface ThemeRepository extends JpaRepository<Theme, Integer> {

    /**
     * Finds a Theme entity by its name.
     *
     * @param name the name of the theme
     * @return the {@link Theme} entity with the specified name, or null if not found
     */
    Theme findByName(String name);
}
