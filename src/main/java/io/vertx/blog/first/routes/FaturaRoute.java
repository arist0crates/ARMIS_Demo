package io.vertx.blog.first.routes;

import io.vertx.blog.first.persistence.FaturaRepo;
import io.vertx.core.json.Json;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.RoutingContext;

public class FaturaRoute {
    private JDBCClient jdbc;

    public FaturaRoute(JDBCClient jdbc){
        this.jdbc = jdbc;
    }

    public void getAllFaturas(RoutingContext routingContext) {
        //If the "?insertUser= " parameter was specified, call the auxiliary handler for it
        final String insertUser = routingContext.request().getParam("insertUser");
        if (insertUser != null) {
            getAllFaturasByInsertUser(insertUser, routingContext);
        }
        else{
            //General query, with no parameters
            jdbc.getConnection(ar -> {
                SQLConnection connection = ar.result();
                FaturaRepo.findManyFaturas(connection, result -> {
                    if (result.succeeded()) {
                        routingContext.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(Json.encodePrettily(result.result()));
                    } else {
                        routingContext.response()
                            .setStatusCode(404).end();
                    }
                    connection.close();
                });
            });
        }        
    }

    public void getOneFaturaByID(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            jdbc.getConnection(ar -> {
                SQLConnection connection = ar.result();
                FaturaRepo.findOneFatura(id, connection, result -> {
                    if (result.succeeded()) {
                        routingContext.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(Json.encodePrettily(result.result()));
                    } else {
                        routingContext.response()
                            .setStatusCode(404).end();
                    }
                    connection.close();
                });
            });
        }
    }

    /**
     * Auxiliary handler for query "?insertUser=[someUsername]"
     */
    private void getAllFaturasByInsertUser(String insertUser, RoutingContext routingContext) {
        jdbc.getConnection(ar -> {
            SQLConnection connection = ar.result();
            FaturaRepo.findFaturasByInsertUser(insertUser, connection, result -> {
                if (result.succeeded()) {
                    routingContext.response()
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(result.result()));
                } else {
                    routingContext.response()
                        .setStatusCode(404).end();
                }
                connection.close();
            });
        });
    }
}