package io.vertx.blog.first.routes;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.Config;
import io.vertx.blog.first.MyFirstVerticle;

@RunWith(VertxUnitRunner.class)
public class FaturaRouteTest {

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
  public void checkThatGetAllFaturasPendentesByInsertUserResponds(TestContext context) {
    Async async = context.async();
    String insertUser = "manuel.meireles";
    String apiURL = "/api/faturas/"+insertUser+"/pendentes";
    vertx.createHttpClient().getNow(port, "localhost", apiURL, response -> {
      response.handler(body -> {
        context.assertEquals(response.statusCode(), 200);
        context.assertEquals(response.headers().get("content-type"), "application/json; charset=utf-8");
        async.complete();
      });
    });
  }
}