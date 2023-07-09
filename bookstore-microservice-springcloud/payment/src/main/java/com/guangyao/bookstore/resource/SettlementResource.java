package com.guangyao.bookstore.resource;

import com.guangyao.bookstore.application.PaymentApplicationService;
import com.guangyao.bookstore.domain.Payment;
import com.guangyao.bookstore.domain.security.Role;
import com.guangyao.bookstore.domain.validation.SufficientStock;
import com.guangyao.bookstore.dto.Settlement;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/settlements")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SettlementResource {

    @Inject
    private PaymentApplicationService service;

    @POST
    @RolesAllowed(Role.USER)
    public Payment executeSettlement(@Valid @SufficientStock Settlement settlement) {
        return service.executeBySettlement(settlement);
    }
}
