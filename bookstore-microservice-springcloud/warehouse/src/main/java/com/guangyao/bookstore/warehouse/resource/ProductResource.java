package com.guangyao.bookstore.warehouse.resource;

import com.guangyao.bookstore.domain.security.Role;
import com.guangyao.bookstore.domain.warehouse.Product;
import com.guangyao.bookstore.infrastructure.jaxrs.CommonResponse;
import com.guangyao.bookstore.warehouse.Application.ProductApplicationService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/products")
@Component
@CacheConfig(cacheNames = "resource.product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductApplicationService service;

    @GET
    @Cacheable(key = "'ALL_PRODUCT'")
    public Iterable<Product> getAllProducts() { return service.getAllProducts(); }

    @GET
    @Path("/{d}")
    @Cacheable(key = "#id")
    public Product getProduct(@PathParam("id") Integer id) { return service.getProduct(id); }

    @PUT
    @Caching(evict = {
            @CacheEvict(key = "#product.id"),
            @CacheEvict(key = "'ALL_PRODUCT'")
    })
    @RolesAllowed(Role.ADMIN)
    @PreAuthorize("oauth2.hasAnyScope('BROWSER')")
    public Response updateProduct(@Valid Product product) {
        return CommonResponse.op(() -> service.saveProduct(product));
    }

    @POST
    @Caching(evict = {
            @CacheEvict(key = "#product.id"),
            @CacheEvict(key = "'ALL_PRODUCT'")
    })
    @RolesAllowed(Role.ADMIN)
    @PreAuthorize("oauth2.hasAnyScope('BROWSER')")
    public Product createProduct(@Valid Product product) { return service.saveProduct(product); }

    @DELETE
    @Caching(evict = {
            @CacheEvict(key = "#product.id"),
            @CacheEvict(key = "'ALL_PRODUCT'")
    })
    @RolesAllowed(Role.ADMIN)
    @PreAuthorize("oauth2.hasAnyScope('BROWSER')")
    public Response removeProduct(@PathParam("id") Integer id) {
        return CommonResponse.op(() -> service.removeProduct(id));
    }
}
