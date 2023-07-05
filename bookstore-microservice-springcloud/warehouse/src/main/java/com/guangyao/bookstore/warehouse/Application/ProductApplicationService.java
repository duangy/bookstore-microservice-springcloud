package com.guangyao.bookstore.warehouse.Application;

import com.guangyao.bookstore.domain.warehouse.Product;
import com.guangyao.bookstore.warehouse.domain.ProductService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Transactional
@Named
public class ProductApplicationService {

    @Inject
    ProductService service;

    public Iterable<Product> getAllProducts() { return service.getAllProducts(); }

    public Product getProduct(Integer id) { return service.getProduct(id); }

    public Product saveProduct(Product product) { return service.saveProduct(product); }

    public void removeProduct(Integer id) { service.deleteProduct(id); }
}
