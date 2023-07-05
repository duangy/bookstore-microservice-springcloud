package com.guangyao.bookstore.warehouse.domain;

import com.guangyao.bookstore.domain.warehouse.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Collection<Product> findByIdIn(Collection<Integer> ids);
}
