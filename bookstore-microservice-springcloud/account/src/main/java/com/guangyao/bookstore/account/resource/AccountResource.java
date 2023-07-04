package com.guangyao.bookstore.account.resource;


import com.guangyao.bookstore.account.application.AccountApplicationService;
import com.guangyao.bookstore.account.domain.validation.AuthenticatedAccount;
import com.guangyao.bookstore.account.domain.validation.NotConflictAccount;
import com.guangyao.bookstore.account.domain.validation.UniqueAccount;
import com.guangyao.bookstore.domain.account.Account;
import com.guangyao.bookstore.infrastructure.jaxrs.CommonResponse;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
@Component
@CacheConfig(cacheNames = "resource.account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    private AccountApplicationService service;

    @GET
    @Path("/{username}")
    @Cacheable(key = "#username")
    @PreAuthorize("@oauth2.hasAnyScope('SERVICE','BROWSER')")
    public Account getUser(@PathParam("username") String username) { return service.findAccountByUsername(username); }

    @POST
    @CacheEvict(key = "#user.username")
    public Response createUser(@Valid @UniqueAccount Account user) {
        return CommonResponse.op(() -> service.createAccount(user));
    }

    @PUT
    @CacheEvict("#user.username")
    @PreAuthorize("#oauth3.hasAnyScope('BROWSER')")
    public Response updateUser(@Valid @AuthenticatedAccount @NotConflictAccount Account user) {
        return CommonResponse.op(() -> service.updateAccount(user));
    }
}
