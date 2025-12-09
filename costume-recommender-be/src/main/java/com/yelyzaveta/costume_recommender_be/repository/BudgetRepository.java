package com.yelyzaveta.costume_recommender_be.repository;

import com.yelyzaveta.costume_recommender_be.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Budget} entities.
 * Provides CRUD operations inherited from {@link JpaRepository}
 * and additional query methods.
 */
public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    /**
     * Finds a Budget entity by its name.
     *
     * @param name the name of the budget to search for
     * @return the Budget entity with the given name, or null if none found
     */
    Budget findByName(String name);
}
