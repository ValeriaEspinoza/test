package com.lakatuna.microservices;

import com.lakatuna.microservices.routes.Users;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * MainVerticle:
 * -Setup the mongoDb client.
 * -Expose the rest routes.
 *
 * @Version 1.0.
 * @Autor Jose Medina.
 */
public class MainVerticle extends AbstractVerticle {
    MongoClient mongoSharedClient = null;

    @Override
    public void start(Promise<Void> startFuture) throws Exception {
        prepareDatabase().compose(v -> startHttpServer()).onComplete(voidAsyncResult -> {
            if (voidAsyncResult.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail("We have a problems!");
            }
        });
    }

    private Future<Void> prepareDatabase() {
        Promise<Void> promise = Promise.promise();

        JsonObject config = Vertx.currentContext().config();

        String uri = config.getString("mongo_uri");
        if (uri == null) {
            uri = "mongodb://localhost:27017";
        }
        String db = config.getString("mongo_db");
        if (db == null) {
            db = "test";
        }

        JsonObject mongoconfig = new JsonObject()
                .put("connection_string", uri)
                .put("db_name", db);

        mongoSharedClient = MongoClient.create(vertx, mongoconfig);
        if (mongoSharedClient != null) {
            promise.complete();
        } else {
            promise.fail("Mongo db notting connection");
        }


        return promise.future();
    }

    private Future<Void> startHttpServer() {
        Promise<Void> promises = Promise.promise();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.get("/prueba").handler(this::indexHandler);
        router.post("/users/create").handler(Users::create);
        router.post().handler(BodyHandler.create());

        server.requestHandler(router)
                .listen(9090, ar -> {
                    if (ar.succeeded()) {
                        promises.complete();
                    } else {
                        promises.fail(ar.cause());
                    }
                });

        return promises.future();
    }

    private void indexHandler(RoutingContext routingContext) {
        routingContext.response().setStatusCode(500)
                .putHeader("Content-Type", "text/html")
                .end("<h1> Hola como estas <h1>");
    }
}
