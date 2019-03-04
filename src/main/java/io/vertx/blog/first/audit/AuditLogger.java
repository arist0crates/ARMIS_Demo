package io.vertx.blog.first.audit;

import java.sql.Timestamp;
import java.util.Date;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.SQLConnection;

public class AuditLogger{
    private static void log(SQLConnection connection, String operation, String username) {
        String query = "INSERT INTO Audit(OperationTimeStamp, Operation, Username) values(?, ?, ?)";

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        JsonArray parameters = new JsonArray();
        parameters.add(timestamp.toString());
        parameters.add(operation);
        parameters.add(username);

        connection.updateWithParams(query, parameters, ar -> {
            if (ar.failed()) {
                System.out.println("Failed to log " + "'" + operation + "'" + " for user " + username);
                System.out.println(ar.cause());
            } else {
                System.out.println("Logged " + "'" + operation + "'" + " for user " + username);
            }
        });
    }

    public static void logConsult(SQLConnection connection, String username){
        String operation = username + "'s info was consulted";
        log(connection, operation, username);
    }
}