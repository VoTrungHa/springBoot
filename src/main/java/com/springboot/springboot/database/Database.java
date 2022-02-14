package com.springboot.springboot.database;

import com.springboot.springboot.Models.Product;
import com.springboot.springboot.repositories.ProductRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {

    private final Logger logger = LoggerFactory.getLogger(Database.class);


    @Bean
    // created facker data for product
    CommandLineRunner commandLineRunner(ProductRepositories productRepositories) {

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Product productA = new Product(  "Iphone", 2021, 125.5555, "test");
//                Product productB = new Product(  "Iphone", 2024, 125.5555, "test");
//                logger.info("insert data" + productRepositories.save(productA));
//                logger.info("insert data" + productRepositories.save(productB));

            }
        };
    }

}
