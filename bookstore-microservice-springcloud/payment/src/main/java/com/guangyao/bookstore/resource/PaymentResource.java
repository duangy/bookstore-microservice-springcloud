package com.guangyao.bookstore.resource;

import com.guangyao.bookstore.application.PaymentApplicationService;
import com.guangyao.bookstore.domain.Payment;
import com.guangyao.bookstore.domain.account.Account;
import com.guangyao.bookstore.domain.security.Role;
import com.guangyao.bookstore.infrastructure.jaxrs.CommonResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pay")
@Component
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    private PaymentApplicationService service;

    @PATCH
    @Path("/{payId}")
    @RolesAllowed(Role.USER)
    public Response updatePaymentState(@PathParam("payId") String payId, @QueryParam("state")Payment.State state) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return updatePaymentStateAlias(payId, account.getId(), state);
    }

    @GET
    @Path("/modify/{payId}")
    public Response updatePaymentStateAlias(@PathParam("payId") String payId, @QueryParam("accountId") Integer accountId,
                                            @QueryParam("state")Payment.State state) {
        if (state == Payment.State.PAYED) {
            return CommonResponse.op(() -> service.accomplishPayment(accountId, payId));
        } else {
            return CommonResponse.op(() -> service.cancelPayment(payId));
        }
    }
}
