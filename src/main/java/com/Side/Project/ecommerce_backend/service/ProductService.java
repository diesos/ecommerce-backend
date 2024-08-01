package com.Side.Project.ecommerce_backend.service;

import com.Side.Project.ecommerce_backend.models.Product;
import com.Side.Project.ecommerce_backend.models.dao.ProductDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    private ProductDAO productDAO;


    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getProducts(){
        return productDAO.findAll();
    }
}
