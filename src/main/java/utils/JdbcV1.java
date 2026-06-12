package utils;

import java.sql.*;

public class JdbcV1 {
    // 1. Tên Driver cho SQL Server
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    // 2. URL kết nối
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=EmployeeDB;encrypt=false;trustServerCertificate=true";
    
    // 3. Tài khoản và mật khẩu SQL Server
    private static final String USER = "sa";
    private static final String PASS = "123";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("LỖI: Không tìm thấy Driver SQL Server (mssql-jdbc)!");
            e.printStackTrace();
        }
    }

    /**
     * Lấy kết nối tới cơ sở dữ liệu
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Truy vấn dữ liệu (V1: Không tham số)
     */
    public static ResultSet executeQuery(String sql) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    /**
     * Cập nhật dữ liệu (V1: Không tham số)
     */
    public static int executeUpdate(String sql) throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    public static void main(String[] args) {
        System.out.println("Đang thử kết nối tới: " + URL);
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối SQL Server THÀNH CÔNG.");
            }
        } catch (SQLException e) {
            System.err.println("Kết nối THẤT BẠI!");
            e.printStackTrace();
        }
    }
}
