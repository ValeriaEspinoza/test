package com.lakatuna.microservices;

import com.lakatuna.microservices.verticles.db.services.CrudService;
import com.lakatuna.microservices.verticles.db.services.CrudVerticle;
import com.lakatuna.microservices.verticles.http.HttpVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;


/**
 * Main Verticle:
 * -Expose the rest routes.
 *
 * @Version 1.0.
 * @Autor Jose Medina.
 */
public class MainVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

    private CrudService dbService;

    @Override
    public void start(Promise<Void> startFuture) throws Exception {
        startAllVerticles().onComplete(voidAsyncResult -> {
            if (voidAsyncResult.succeeded()) {
                logger.debug("Server execute");
                startFuture.complete();
            } else {
                logger.debug("startup errors");
                startFuture.fail("We have a problems!");
            }
        });
    }

    public Future<Void> startAllVerticles() {
        Promise<Void> promises = Promise.promise();

        Promise<String> promiseCrudVerticle = Promise.promise();

        vertx.deployVerticle(new CrudVerticle(), new DeploymentOptions().setConfig(config()), promiseCrudVerticle);
        promiseCrudVerticle.future().compose(id -> {
            Promise<String> promiseHttpVerticle = Promise.promise();

            vertx.deployVerticle(
                    HttpVerticle.class.getName(),
                    new DeploymentOptions().setInstances(2).setConfig(config()),
                    promiseHttpVerticle);

            return promiseHttpVerticle.future();
        });
        promises.complete();
        return promises.future();
    }


}
