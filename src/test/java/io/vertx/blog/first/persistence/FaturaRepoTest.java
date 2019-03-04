package io.vertx.blog.first.persistence;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.Config;
import io.vertx.blog.first.MyFirstVerticle;

@RunWith(VertxUnitRunner.class)
public class FaturaRepoTest {

  private Vertx vertx;
  private int port;
  private JDBCClient jdbc;

  @Before
  public void setUp(TestContext context) {
    vertx = Vertx.vertx();
    port = 8081;
    JsonObject jdbcConfig = new JsonObject()
        .put("url", Config.databaseConnectionString)
        .put("driver_class", Config.databaseDriverClass);
    // Create a JDBC client
    jdbc = JDBCClient.createShared(vertx, jdbcConfig);
    
    DeploymentOptions options = new DeploymentOptions()
        .setConfig(new JsonObject()
            .put("http.port", port)
            .put("url", Config.databaseConnectionString)
            .put("driver_class", Config.databaseDriverClass)
        );
    vertx.deployVerticle(MyFirstVerticle.class.getName(), options, context.asyncAssertSuccess());
  }

  @After
  public void tearDown(TestContext context) {
    jdbc.close();
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void checkThatFindFaturasPendentesByInsertUserResponds(TestContext context) {
    Async async = context.async();
    String insertUser = "manuel.meireles";
    jdbc.getConnection(ar -> {
        SQLConnection connection = ar.result();
        FaturaRepo.findFaturasByInsertUser(insertUser, connection, result -> {
            context.assertTrue(result.succeeded(), "Query did not succeed");
            context.assertTrue(!result.result().isEmpty(), "Query is empty");

            // VERY IMPORTANT - tests only work properly with this. Otherwise, they don't run asyncronously
            async.complete();
        });
    });
  }
}