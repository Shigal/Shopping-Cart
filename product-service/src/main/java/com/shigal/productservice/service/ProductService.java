package com.shigal.productservice.service;/*
 *
 * @author Lawshiga
 *
 */

import com.shigal.productservice.dto.ProductRequest;
import com.shigal.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    public void createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}
