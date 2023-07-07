package com.guangyao.bookstore.domain.client;

import com.guangyao.bookstore.domain.warehouse.DeliveredStatus;
import com.guangyao.bookstore.domain.warehouse.Product;
import com.guangyao.bookstore.domain.warehouse.Stockpile;
import com.guangyao.bookstore.dto.Settlement;
import org.springframework.cloud.openfeign.FeignClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FeignClient(name = "warehouse")
public interface ProductServiceClient {

    @GET
    @Path("/restful/products/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Product getProduct(@PathParam("id") Integer id);

    @GET
    @Path("/restful/products")
    @Consumes(MediaType.APPLICATION_JSON)
    Product[] getProducts();

    @PATCH
    @Path("/restful/products/stockpile/delivered/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    void setDeliveredStatus(@PathParam("productId") Integer productId, @QueryParam("status") DeliveredStatus status,
                            @QueryParam("amount") Integer amount);

    @GET
    @Path("/restful/products/stockpile/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    Stockpile queryStockPile(@PathParam("productId") Integer productId);

    default void decrease(Integer productId, Integer amount) {
        setDeliveredStatus(productId, DeliveredStatus.DECREASE, amount);
    }

    default void increase(Integer productId, Integer amount) {
        setDeliveredStatus(productId, DeliveredStatus.INCREASE, amount);
    }

    default void frozen(Integer productId, Integer amount) {
        setDeliveredStatus(productId, DeliveredStatus.FROZEN, amount);
    }

    default void thawed(Integer productId, Integer amount) {
        setDeliveredStatus(productId, DeliveredStatus.THAWED, amount);
    }

    default void replenishProductInformation(Settlement bill) {
        bill.productMap = Stream.of(getProducts()).collect(Collectors.toMap(Product::getId, Function.identity()));
    }
}
