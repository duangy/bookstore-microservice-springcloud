package com.guangyao.bookstore.infrastructure.jaxrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.Consumer;

public abstract class CommonResponse {

    private static final Logger log = LoggerFactory.getLogger(CommonResponse.class);

    public static Response send(Response.Status status, String message) {
        Integer code = status.getFamily() == Response.Status.Family.SUCCESSFUL ? CodeMessage.CODE_SUCCESS : CodeMessage.CODE_DEFAULT_FAILURE;
        return Response.status(status).type(MediaType.APPLICATION_JSON).entity(new CodeMessage(code, message)).build();
    }

    public static Response failure(String message) { return send(Response.Status.INTERNAL_SERVER_ERROR, message); }

    public static Response success(String message) { return send(Response.Status.OK, message); }

    public static Response success() { return send(Response.Status.OK, "操作成功"); }

    public static Response op(Runnable executor) { return op(executor, e -> log.error(e.getMessage(), e)); }

    public static Response op(Runnable executor, Consumer<Exception> exceptionConsumer) {
        try {
            executor.run();
            return CommonResponse.success();
        } catch (Exception e) {
            exceptionConsumer.accept(e);
            return CommonResponse.failure(e.getMessage());
        }
    }
}
