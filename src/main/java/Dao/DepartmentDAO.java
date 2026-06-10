package Dao;

import entity.Department;
import utils.JdbcV1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
        String sql = "{CALL spSelectAll}";
        try (Connection conn = JdbcV1.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                list.add(new Department(
                        rs.getString("Id").trim(),
                        rs.getString("Name"),
                        rs.getString("Description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Department getById(String id) {
        String sql = "{CALL spSelectById(?)}";
        try (Connection conn = JdbcV1.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return new Department(
                            rs.getString("Id").trim(),
                            rs.getString("Name"),
                            rs.getString("Description")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Department dept) {
        String sql = "{CALL spInsert(?, ?, ?)}";
        try (Connection conn = JdbcV1.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, dept.getId());
            cs.setString(2, dept.getName());
            cs.setString(3, dept.getDescription());
            cs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Department dept) {
        String sql = "{CALL spUpdate(?, ?, ?)}";
        try (Connection conn = JdbcV1.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, dept.getId());
            cs.setString(2, dept.getName());
            cs.setString(3, dept.getDescription());
            cs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "{CALL spDeleteById(?)}";
        try (Connection conn = JdbcV1.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, id);
            cs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
