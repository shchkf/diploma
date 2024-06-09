package org.manager.database;

import java.sql.*;
import java.util.Properties;

public class PostgresqlConnector {

    private static final String DRIVER_CLASS = "org.postgresql.Driver";
    private static final String JDBC_URL_TEMPLATE = "jdbc:postgresql://%s:%d/%s";

    private String host;
    private int port;
    private String database;
    private String user;
    private String password;
    private Connection connection;

    public PostgresqlConnector(String host, int port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public void connect() throws SQLException, ClassNotFoundException {
        // Load the JDBC driver
        Class.forName(DRIVER_CLASS);

        // Create the JDBC URL
        String jdbcUrl = String.format(JDBC_URL_TEMPLATE, host, port, database);

        // Create the connection properties
        Properties properties = new Properties();
        properties.put("user", user);
        properties.put("password", password);

        // Connect to the database
        connection = DriverManager.getConnection(jdbcUrl, properties);
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        // Create a statement
        Statement statement = connection.createStatement();

        // Execute the query
        return statement.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        // Create a statement
        Statement statement = connection.createStatement();

        // Execute the update
        return statement.executeUpdate(query);
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        // Prepare the statement
        return connection.prepareStatement(query);
    }

    public Connection getConnection() {
        return connection;
    }
}
