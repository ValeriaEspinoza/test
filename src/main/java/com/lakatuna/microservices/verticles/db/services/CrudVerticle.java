package com.lakatuna.microservices.verticles.db.services;

import com.lakatuna.microservices.VerticleConfigurations;
import com.lakatuna.microservices.constants.Constants;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ServiceBinder;

public class CrudVerticle extends AbstractVerticle implements VerticleConfigurations {
    private static final Logger logger = LoggerFactory.getLogger(CrudVerticle.class);

    @Override
    public void start(Promise<Void> startFuture) throws Exception {
        checkEnvConfigurations().compose(this::execute).onComplete(
                voidAsyncResult -> {
                    if (voidAsyncResult.succeeded()) {
                        logger.debug("Database execute");
                        startFuture.complete();
                    } else {
                        logger.debug("Database startup errors");
                        startFuture.fail("We have a problems!");
                    }
                }
        );
    }


    @Override
    public Future<JsonObject> checkEnvConfigurations() {
        Promise<JsonObject> promise = Promise.promise();

        String uri = config().getString(Constants.CONFIG_DB_URL);
        if (uri == null) {
            uri = "mongodb://localhost:27017";
        }
        String db = config().getString(Constants.CONFIG_DB_NAME);
        if (db == null) {
            db = "test";
        }

        JsonObject mongoconfig = new JsonObject()
                .put("connection_string", uri)
                .put("db_name", db);

        promise.complete(mongoconfig);
        return promise.future();
    }


    @Override
    public Future<Void> execute(JsonObject configurationToExecute) {
        Promise<Void> promise = Promise.promise();

        MongoClient mongoSharedClient = MongoClient.create(vertx, configurationToExecute);

        CrudService.create(mongoSharedClient, crudServiceAsyncResult -> {
            if (crudServiceAsyncResult.succeeded()) {
                ServiceBinder serviceBinder = new ServiceBinder(vertx);
                serviceBinder.setAddress(Constants.CONFIG_DB_QUEUE);
                serviceBinder.register(CrudService.class, crudServiceAsyncResult.result());
                promise.complete();
            } else {
                promise.fail("Mongo db never connection");
            }
        });

        return promise.future();
    }
}
