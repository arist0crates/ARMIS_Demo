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
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.SQLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.vertx.blog.first.audit.AuditLogger;
/**
 *
 * @author rodrigo.soares
 */
public class FaturaRepo {

    // public static void findOneFatura(String id, SQLConnection connection, Handler<AsyncResult<Fatura>> resultHandler) {
    //     String query = " SELECT * FROM Fatura, EstadoFatura, Fornecedor WHERE Fatura.FaturaID=? AND EstadoFatura.EstadoFaturaID = Fatura.EstadoFaturaID AND Fatura.FornecedorID = Fornecedor.FornecedorID";

    //     connection.queryWithParams(query, new JsonArray().add(id), ar -> {
    //         if (ar.failed()) {
    //             resultHandler.handle(Future.failedFuture("Nao existem Faturas"));
    //         } else {
    //             if (ar.result().getNumRows() >= 1) {
    //                 Fatura result = buildFatura(ar.result().getRows().get(0));
    //                 resultHandler.handle(Future.succeededFuture(result));
    //             } else {
    //                 resultHandler.handle(Future.failedFuture("Nao existem Faturas"));
    //             }
    //         }
    //     });
    // }

    // public static void findManyFaturas(SQLConnection connection, Handler<AsyncResult<Collection<Fatura>>> resultHandler) {
    //     String query = " SELECT * FROM Fatura, EstadoFatura, Fornecedor WHERE EstadoFatura.EstadoFaturaID = Fatura.EstadoFaturaID AND Fatura.FornecedorID = Fornecedor.FornecedorID";

    //     connection.query(query, ar -> {
    //         if (ar.failed()) {
    //             resultHandler.handle(Future.failedFuture("Nao existem Faturas"));
    //         } else {
    //             if (ar.result().getNumRows() >= 1) {
    //                 Collection<Fatura> faturas = buildFaturas(ar.result().getRows());
    //                 resultHandler.handle(Future.succeededFuture(faturas));
    //             } else {
    //                 resultHandler.handle(Future.failedFuture("Nao existem Faturas"));
    //             }
    //         }
    //     });
    // }

    public static void findFaturasByInsertUser(String insertUser, SQLConnection connection, Handler<AsyncResult<Collection<Fatura>>> resultHandler) {
        String query = " SELECT TOP 1000 * FROM Fatura, EstadoFatura, Fornecedor WHERE Fatura.InsertUser=? AND EstadoFatura.EstadoFaturaID = Fatura.EstadoFaturaID AND Fatura.FornecedorID = Fornecedor.FornecedorID ORDER BY FaturaID DESC";

        connection.queryWithParams(query, new JsonArray().add(insertUser), ar -> {
            if (ar.failed()) {
                resultHandler.handle(Future.failedFuture("Nao existem Faturas"));
            } else {
                if (ar.result().getNumRows() >= 1) {
                    Collection<Fatura> faturas = buildFaturas(ar.result().getRows());
                    resultHandler.handle(Future.succeededFuture(faturas));
                } else {
                    resultHandler.handle(Future.failedFuture("Nao existem Faturas para o User " + insertUser));
                }
            }
        });

        AuditLogger.logConsult(connection, insertUser);
    }

    public static void findFaturasPendentesByInsertUser(String insertUser, SQLConnection connection, Handler<AsyncResult<Collection<Fatura>>> resultHandler) {
        String query = " SELECT TOP 1000 * FROM Fatura, EstadoFatura, Fornecedor WHERE Fatura.InsertUser=? AND EstadoFatura.DescritivoEstadoFatura='Por Aprovar' AND EstadoFatura.EstadoFaturaID = Fatura.EstadoFaturaID AND Fatura.FornecedorID = Fornecedor.FornecedorID ORDER BY FaturaID DESC";

        connection.queryWithParams(query, new JsonArray().add(insertUser), ar -> {
            if (ar.failed()) {
                resultHandler.handle(Future.failedFuture("Nao existem Faturas"));
            } else {
                if (ar.result().getNumRows() >= 1) {
                    Collection<Fatura> faturas = buildFaturas(ar.result().getRows());
                    resultHandler.handle(Future.succeededFuture(faturas));
                } else {
                    resultHandler.handle(Future.failedFuture("Nao existem Faturas para o User " + insertUser));
                }
            }
        });

        AuditLogger.logConsult(connection, insertUser);
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
