package com.shigal.productservice.repository;/*
 *
 * @author Lawshiga
 *
 */

import com.shigal.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
