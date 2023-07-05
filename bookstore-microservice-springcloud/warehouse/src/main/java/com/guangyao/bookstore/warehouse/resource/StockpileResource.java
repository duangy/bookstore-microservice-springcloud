package com.guangyao.bookstore.warehouse.resource;

import com.guangyao.bookstore.domain.security.Role;
import com.guangyao.bookstore.domain.warehouse.DeliveredStatus;
import com.guangyao.bookstore.domain.warehouse.Stockpile;
import com.guangyao.bookstore.infrastructure.jaxrs.CommonResponse;
import com.guangyao.bookstore.warehouse.Application.StockpileApplicationService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/products")
@Component
@CacheConfig(cacheNames = "resource.product")
@Produces(MediaType.APPLICATION_JSON)
public class StockpileResource {

    @Inject
    private StockpileApplicationService service;

    @PATCH
    @Path("/stockpile/{productId}")
    @RolesAllowed(Role.ADMIN)
    @PreAuthorize("#oauth2.hasAnyScope('BROWSER')")
    public Response updateStockpile(@PathParam("productId") Integer productId, @QueryParam("amount") Integer amount) {
        return CommonResponse.op(() -> service.setStockpileAmountByProductId(productId, amount));
    }

    @GET
    @Path("/stockpile/{productId}")
    @RolesAllowed(Role.ADMIN)
    @PreAuthorize("#oauth2.hasAnyScope('BROWSER','SERVICE')")
    public Stockpile queryStockpile(@PathParam("productId") Integer productId) {
        return service.getStockpile(productId);
    }

    @PATCH
    @Path("/stockpile/delivered/{productId}")
    @PreAuthorize("#oauth2.hasAnyScope('SERVICE')")
    public Response setDeliveredStatus(@PathParam("productId") Integer productId,
                                       @QueryParam("status")DeliveredStatus status,
                                       @QueryParam("amount") Integer amount) {
        return CommonResponse.op(() -> service.setDeliveredStatus(productId, status, amount));
    }
}
