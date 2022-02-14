package com.springboot.springboot.Controllers;

import com.springboot.springboot.Models.Product;
import com.springboot.springboot.Models.ResponseObject;
import com.springboot.springboot.repositories.ProductRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getProductByIdAndNameProduct
            (@RequestParam(name = "productName",required = false) String productName,
             @RequestParam(name = "year",required = false) int year,
             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
             @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
             @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable = PageRequest.of(0, 5,sortable);
        List<Product> list = productRepositories.findAllByProductNameAndYear(productName,year, pageable);
        return list.size() > 0 ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "success", list))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("400", "Cannot find product", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepositories.findById(id);
        return product.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "success", product))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("400", "Cannot find product id=" + id, null));
    }

    @PostMapping("insert")
    public ResponseEntity<ResponseObject> insertProduct(@RequestBody Product product) {
        // product must not have the same name!
        // JPA suport function check same name;
        List<Product> products = productRepositories.findByProductName(product.getProductName());
        return products.isEmpty() ? ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("200", "insert product success", productRepositories.save(product)))
                : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(new ResponseObject("404", "Product name already taken", ""));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        Product p = productRepositories.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setPrice(newProduct.getPrice());
                    product.setUrl(newProduct.getUrl());
                    return productRepositories.save(product);
                }).orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepositories.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "update product success", p));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteProductById(@PathVariable Long id) {
        boolean exists = productRepositories.existsById(id);
        if (exists) {
            productRepositories.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Delete product success", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("404", "Cannot find product with id =" + id, ""));
    }
}
