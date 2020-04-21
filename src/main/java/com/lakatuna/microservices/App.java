package com.lakatuna.microservices;

import io.vertx.rxjava.core.Vertx;

/**
 *
 * Program boot.
 *
 * @Autor Jose Medina.
 */
public class App {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}
