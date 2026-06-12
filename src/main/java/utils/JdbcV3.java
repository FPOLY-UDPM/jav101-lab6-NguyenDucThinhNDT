package utils;

import java.sql.*;

public class JdbcV3 {
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=EmployeeDB;encrypt=false;trustServerCertificate=true";
    static String username = "sa";
    static String password = "123";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, username, password);
    }

    public static int executeUpdate(String sql, Object... values) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall(sql)) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement.executeUpdate();
        }
    }

    public static ResultSet executeQuery(String sql, Object... values) throws SQLException {
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(sql);
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
        return statement.executeQuery();
    }
}
