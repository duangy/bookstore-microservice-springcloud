package com.guangyao.bookstore.warehouse.domain;

import com.guangyao.bookstore.domain.warehouse.Stockpile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

@Named
public class StockpileService {

    private static final Logger log = LoggerFactory.getLogger(StockpileService.class);

    @Inject
    StockpileRepository repository;

    public Stockpile getByProductId(Integer productId) {
        return repository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
    }

    public void decrease(Integer productId, Integer amount) {
        Stockpile stock = repository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
        stock.decrease(amount);
        repository.save(stock);
        log.info("库存出库，商品：{}，数量：{}，现有库存：{}，现存冻结：{}", productId, amount, stock.getAmount(), stock.getFrozen());
    }

    public void increase(Integer productId, Integer amount) {
        Stockpile stock = repository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
        stock.increate(amount);
        repository.save(stock);
        log.info("库存入库，商品：{}，数量：{}，现有库存：{}，现存冻结：{}", productId, amount, stock.getAmount(), stock.getFrozen());
    }

    public void frozen(Integer productId, Integer amount) {
        Stockpile stock = repository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
        stock.frozen(amount);
        repository.save(stock);
        log.info("冻结库存，商品：{}，数量：{}，现有库存：{}，现存冻结：{}", productId, amount, stock.getAmount(), stock.getFrozen());
    }

    public void thawed(Integer productId, Integer amount) {
        Stockpile stock = repository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
        stock.thawed(amount);
        repository.save(stock);
        log.info("解冻库存，商品：{}，数量：{}，现有库存：{}，现存冻结：{}", productId, amount, stock.getAmount(), stock.getFrozen());
    }

    public void set(Integer productId, Integer amount) {
        Stockpile stock = repository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
        stock.setAmount(amount);
        repository.save(stock);
    }
}
