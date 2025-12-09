package com.yelyzaveta.costume_recommender_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CostumeRecommenderBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CostumeRecommenderBeApplication.class, args);
	}

}
