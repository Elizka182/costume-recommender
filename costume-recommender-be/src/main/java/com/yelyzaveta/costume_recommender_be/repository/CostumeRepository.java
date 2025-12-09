package com.yelyzaveta.costume_recommender_be.repository;

import com.yelyzaveta.costume_recommender_be.entity.Costume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumeRepository extends JpaRepository<Costume, Integer> {

}
