package utils;

import java.sql.*;

public class JdbcV2 {
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=EmployeeDB;encrypt=false;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "123";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static int executeUpdate(String sql, Object... values) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement.executeUpdate();
        }
    }

    public static ResultSet executeQuery(String sql, Object... values) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
        return statement.executeQuery();
    }

    public static PreparedStatement getStmt(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }

    public static ResultSet query(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement stmt = getStmt(conn, sql, args);
        return stmt.executeQuery();
    }
}
