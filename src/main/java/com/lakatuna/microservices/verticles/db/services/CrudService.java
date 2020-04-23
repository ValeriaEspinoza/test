package com.lakatuna.microservices.verticles.db.services;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;


@VertxGen
@ProxyGen
public interface CrudService {
    @GenIgnore
    static CrudServiceImpl create(MongoClient mongoClient, Handler<AsyncResult<CrudService>> readyHandler) {
        return new CrudServiceImpl(mongoClient, readyHandler);
    }

    @GenIgnore
    static CrudService createProxy(Vertx vertx, String address) {
        return new CrudServiceVertxEBProxy(vertx, address);
    }

    @Fluent
    CrudService createUser(String param, Handler<AsyncResult<JsonArray>> resultHandler);

    @Fluent
    CrudService read(int id, String markdown, Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    CrudService update(String title, String markdown, Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    CrudService delete(String name, Handler<AsyncResult<JsonObject>> resultHandler);
}
