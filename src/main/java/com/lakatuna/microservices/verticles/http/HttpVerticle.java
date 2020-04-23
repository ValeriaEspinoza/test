package com.lakatuna.microservices.verticles.http;

import com.lakatuna.microservices.VerticleConfigurations;
import com.lakatuna.microservices.constants.Constants;
import com.lakatuna.microservices.routes.Users;
import com.lakatuna.microservices.verticles.db.services.CrudService;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.Objects;

public class HttpVerticle extends AbstractVerticle implements VerticleConfigurations {
    private static final Logger logger = LoggerFactory.getLogger(HttpVerticle.class);

    private CrudService dbService;

    @Override
    public void start(Promise<Void> startFuture) throws Exception {
        checkEnvConfigurations().compose(this::execute).onComplete(voidAsyncResult -> {
            if (voidAsyncResult.succeeded()) {
                logger.debug("Http verticle deployed");
                startFuture.complete();
            } else {
                logger.debug("Http verticle not deployed");
                startFuture.fail("HttpVerticle in error");
            }
        });
    }

    @Override
    public Future<JsonObject> checkEnvConfigurations() {
        Promise<JsonObject> promises = Promise.promise();

        String dbUrl = config().getString(Constants.CONFIG_DB_URL);
        String errorDb = "Url database empty";
        if (StringUtil.isNullOrEmpty(dbUrl)) {
            logger.debug(errorDb);
            promises.fail(errorDb);
        }

        String dbName = config().getString(Constants.CONFIG_DB_URL);
        errorDb = "database name empty";
        if (StringUtil.isNullOrEmpty(dbName)) {
            logger.debug(errorDb);
            promises.fail(errorDb);
        }

        Integer httpServerPort = config().getInteger(Constants.CONFIG_HTTP_SERVER_PORT);
        if (Objects.isNull(httpServerPort)) {
            String errorPort = "Http server port empty";
            logger.debug(errorPort);
            promises.fail(errorPort);
        }

        promises.complete();

        return promises.future();
    }


    @Override
    public Future<Void> execute(JsonObject configurationToExecute) {
        // not use in this case configurationToExecute

        Promise<Void> promises = Promise.promise();

        String configMongoQueue = config().getString(Constants.CONFIG_DB_QUEUE);
        dbService = CrudService.createProxy(vertx, configMongoQueue);

        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.post("/users/create").handler(this::create);

        Integer port = config().getInteger(Constants.CONFIG_HTTP_SERVER_PORT);
        server.requestHandler(router)
                .listen(port, ar -> {
                    if (ar.succeeded()) {
                        promises.complete();
                    } else {
                        promises.fail(ar.cause());
                    }
                });
        return promises.future();
    }

    private void create(RoutingContext routingContext) {
        Users.create(routingContext, dbService);
    }
}
