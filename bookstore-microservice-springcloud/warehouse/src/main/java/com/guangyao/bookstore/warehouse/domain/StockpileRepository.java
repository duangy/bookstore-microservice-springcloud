package com.guangyao.bookstore.warehouse.domain;

import com.guangyao.bookstore.domain.warehouse.Stockpile;
import org.springframework.data.repository.CrudRepository;

public interface StockpileRepository extends CrudRepository<Stockpile, Integer> {
}
