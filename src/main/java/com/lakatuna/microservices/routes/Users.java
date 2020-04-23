package com.lakatuna.microservices.routes;

import com.lakatuna.microservices.verticles.db.services.CrudService;
import io.vertx.ext.web.RoutingContext;

public class Users {

    private Users() {
    }

    public static void create(RoutingContext routingContext, CrudService vertx) {
        routingContext.response().setStatusCode(200)
                .putHeader("Content-Type", "text/html")
                .end("<h4> Hola <h4>");

        /*vertx.createUser("prueba", jsonArrayAsyncResult -> {
            if (jsonArrayAsyncResult.succeeded()) {
                routingContext.response().setStatusCode(200)
                        .putHeader("Content-Type", "text/html")
                        .end("<h4> Hola <h4>");
            } else {
                routingContext.fail(jsonArrayAsyncResult.cause());
            }
        });*/
    }
}
