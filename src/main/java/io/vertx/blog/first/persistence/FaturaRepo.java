/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.vertx.blog.first.persistence;

import io.vertx.blog.first.model.Fatura;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.RoutingContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author rodrigo.soares
 */
public class FaturaRepo {

    public void findOneFatura(int id, SQLConnection connection, Handler<AsyncResult<Collection<Fatura>>> resultHandler) {
        Collection<Fatura> result = null;

        connection.queryWithParams("SELECT * FROM Fatura WHERE id=?", new JsonArray().add(id), ar -> {
            if (ar.failed()) {
                resultHandler.handle(Future.failedFuture("Nao existem Faturas"));
            } else {
                if (ar.result().getNumRows() >= 1) {
                    resultHandler.handle(Future.succeededFuture(buildFaturas(ar.result().getRows())));
                } else {
                    resultHandler.handle(Future.failedFuture("Nao existem Faturas"));
                }
            }
        });
    }

    public static void findManyFaturas(SQLConnection connection, RoutingContext routingContext) {
        connection.query("SELECT * FROM Fatura", result -> {
            Collection<Fatura> faturas = buildFaturas(result.result().getRows());
            routingContext.response()
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(faturas));
            connection.close();
        });
    }

    private static Fatura buildFatura(JsonObject result) {
        return new Fatura(result);
    }

    private static Collection<Fatura> buildFaturas(List<JsonObject> SQLrows) {
        Collection<Fatura> faturas = new ArrayList<>();
        for (JsonObject row : SQLrows) {
            faturas.add(buildFatura(row));
        }
        return faturas;
    }
}
