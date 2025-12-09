package com.yelyzaveta.costume_recommender_be.repository;

import com.yelyzaveta.costume_recommender_be.entity.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link GroupType} entities.
 */
public interface GroupTypeRepository extends JpaRepository<GroupType, Integer> {

    /**
     * Finds a GroupType entity by its name.
     *
     * @param name the name of the group type
     * @return the {@link GroupType} entity with the specified name, or null if not found
     */
    GroupType findByName(String name);
}
