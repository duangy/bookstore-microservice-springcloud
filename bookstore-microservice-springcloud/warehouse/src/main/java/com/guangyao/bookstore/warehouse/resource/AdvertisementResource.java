package com.guangyao.bookstore.warehouse.resource;

import com.guangyao.bookstore.warehouse.domain.Advertisement;
import com.guangyao.bookstore.warehouse.domain.AdvertisementRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;

@Path("/advertisements")
@Component
@Produces(MediaType.APPLICATION_JSON)
public class AdvertisementResource {

    @Inject
    AdvertisementRepository repository;

    @GET
    @Cacheable("resource.advertisements")
    public Iterable<Advertisement> getAllAdvertisements() { return repository.findAll(); }
}
