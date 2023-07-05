package com.guangyao.bookstore.warehouse.domain;

import com.guangyao.bookstore.domain.warehouse.Product;
import com.guangyao.bookstore.dto.Item;
import com.guangyao.bookstore.dto.Settlement;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named
public class ProductService {

    @Inject
    ProductRepository repository;

    public void replenishProductInformation(Settlement bill) {
        List<Integer> ids = bill.getItems().stream().map(Item::getProductId).collect(Collectors.toList());
        bill.productMap = repository.findByIdIn(ids).stream().collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    public Iterable<Product> getAllProducts() { return repository.findAll(); }

    public Product getProduct(Integer id) { return repository.findById(id).orElse(null); }

    public Product saveProduct(Product product) { return repository.save(product); }

    public void deleteProduct(Integer id) { repository.deleteById(id); }
}
