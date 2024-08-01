package com.Side.Project.ecommerce_backend.models.dao;

import com.Side.Project.ecommerce_backend.models.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
