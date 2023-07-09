package com.guangyao.bookstore.domain.security;

import com.guangyao.bookstore.domain.account.Account;
import org.springframework.cloud.openfeign.FeignClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@FeignClient(name = "account")
public interface AccountServiceClient {

    @GET
    @Path("/restful/accounts/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    Account findByUsername(@PathParam("username") String username);
}
