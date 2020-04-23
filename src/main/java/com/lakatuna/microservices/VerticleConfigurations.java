package com.lakatuna.microservices;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public interface VerticleConfigurations {
    Future<JsonObject> checkEnvConfigurations();

    Future<Void> execute(JsonObject configurationToExecute);
}
