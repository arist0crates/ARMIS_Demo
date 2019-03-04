package io;

public class Config{
    public static final int httpPort = 8080;
    public static final String databaseConnectionString = "jdbc:sqlserver://DRIVE;databaseName=Demo;integratedSecurity=true";
    public static final String databaseDriverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";


    // ROUTES
    public static final String routeGetOneFaturaByID = "/api/faturas/:id";
    public static final String routeGetAllFaturasByInsertUser = "/api/faturas/:insertuser";
    public static final String routeGetAllFaturasPendentesByInsertUser = "/api/faturas/:insertuser/pendentes";
}