package com.guangyao.bookstore.warehouse.Application;

import com.guangyao.bookstore.domain.warehouse.DeliveredStatus;
import com.guangyao.bookstore.domain.warehouse.Stockpile;
import com.guangyao.bookstore.warehouse.domain.StockpileService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Transactional
@Named
public class StockpileApplicationService {

    @Inject
    StockpileService service;

    public Stockpile getStockpile(Integer productId) { return service.getByProductId(productId); }

    public void setStockpileAmountByProductId(Integer productId, Integer amount) { service.set(productId, amount); }

    public void setDeliveredStatus(Integer productId, DeliveredStatus status, Integer amount) {
        switch (status) {
            case DECREASE:
                service.decrease(productId, amount);
                break;
            case INCREASE:
                service.increase(productId, amount);
                break;
            case FROZEN:
                service.frozen(productId, amount);
                break;
            case THAWED:
                service.thawed(productId, amount);
                break;
        }
    }
}
