package com.lakatuna.microservices.routes;

import io.vertx.ext.web.RoutingContext;

public class Users {
    public static void create(RoutingContext routingContext) {
        routingContext.response().setStatusCode(200)
                .putHeader("Content-Type", "text/html")
                .end("<h4> Hola <h4>");
    }
}
