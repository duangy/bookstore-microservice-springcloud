package com.guangyao.bookstore.domain.client;

import com.guangyao.bookstore.domain.warehouse.Stockpile;

import javax.ws.rs.PathParam;

public interface ProductServiceClient {
    Stockpile queryStockpile(@PathParam("productId") Integer productId);
}
