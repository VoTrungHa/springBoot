package com.springboot.springboot.Controllers;
import com.springboot.springboot.Models.Product;
import com.springboot.springboot.repositories.ProductRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/product")
public class ProductController {
    //DI=Dependency ịnection

    @Autowired
    private ProductRepositories productRepositories;

    @GetMapping("")
    public List<Product> getAllProducts() {
        return productRepositories.findAll();
    }

}
