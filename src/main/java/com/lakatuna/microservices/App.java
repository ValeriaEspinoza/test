package com.lakatuna.microservices;

import io.vertx.rxjava.core.Vertx;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}
