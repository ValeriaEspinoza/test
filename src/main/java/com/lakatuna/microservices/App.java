package com.lakatuna.microservices;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;

/**
 * Program boot.
 *
 * @Autor Jose Medina.
 */
public class App {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        JsonObject config = new JsonObject();
        config.put("db.url", "mongodb://localhost:27017");
        config.put("db.name", "test");
        config.put("http.server.port", 9090);
        config.put("db.queue", "mongo.db.queue");

        vertx.deployVerticle(new MainVerticle(), new DeploymentOptions().setConfig(config));
    }
}
