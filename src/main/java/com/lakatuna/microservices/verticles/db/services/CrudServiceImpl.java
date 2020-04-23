package com.lakatuna.microservices.verticles.db.services;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class CrudServiceImpl implements CrudService {
    private MongoClient mongoClient = null;

    public CrudServiceImpl(MongoClient mongoClient, Handler<AsyncResult<CrudService>> readyHandler) {
        this.mongoClient = mongoClient;

        readyHandler.handle(Future.succeededFuture(this));
    }

    @Override
    public CrudService createUser(String param, Handler<AsyncResult<JsonArray>> resultHandler) {

        this.mongoClient.getCollections(listAsyncResult -> {

            if (listAsyncResult.succeeded()) {
                listAsyncResult.result().forEach(System.out::println);
            }
            resultHandler.handle(Future.succeededFuture(new JsonArray()));
        });

        return this;
    }

    @Override
    public CrudService read(int id, String markdown, Handler<AsyncResult<Void>> resultHandler) {
        return this;
    }

    @Override
    public CrudService update(String title, String markdown, Handler<AsyncResult<Void>> resultHandler) {
        return this;
    }

    @Override
    public CrudService delete(String name, Handler<AsyncResult<JsonObject>> resultHandler) {
        return this;
    }
}
