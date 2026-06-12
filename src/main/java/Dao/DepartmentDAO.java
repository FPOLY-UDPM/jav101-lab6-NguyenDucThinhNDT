package Dao;

import entity.Department;
import utils.JdbcV1;
import utils.JdbcV2;
import utils.JdbcV3;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    //JDBC V1
    private String stmSELECT = "SELECT [Id], [Name], [Description] FROM [dbo].[Departments]";

    //JDBC V2
    private String stmSELECT_byId = "SELECT [Id], [Name], [Description] FROM [dbo].[Departments] WHERE [Id] = ?";
    private String stmSELECT_byName = "SELECT [Id], [Name], [Description] FROM [dbo].[Departments] WHERE [Name] LIKE ?";

    // Câu lệnh SQL phục vụ CRUD
    private String stmINSERT = "INSERT INTO [dbo].[Departments] ([Id], [Name], [Description]) VALUES (?, ?, ?)";
    private String stmUPDATE = "UPDATE [dbo].[Departments] SET [Name] = ?, [Description] = ? WHERE [Id] = ?";
    private String stmDELETE = "DELETE FROM [dbo].[Departments] WHERE [Id] = ?";

    //JDBC V3
    // Câu lệnh call stored procedure
    private String callSELECT = "exec spSelectAll";
    private String callSELECT_byId = "exec spSelectById(?)";
    private String callINSERT = "exec spInsert(?,?,?)";
    private String callUPDATE = "exec spUpdate(?,?,?)";
    private String callDELETE_byId = "exec spDeleteById(?)";

    // Cách viết này chỉ để test nhanh, VI PHẠM QUY ĐỊNH MÔ HÌNH MVC
    public void checkDepartmentDAO() {
        try {
            String sql = stmSELECT;
            ResultSet resultSet = JdbcV1.executeQuery(sql);
            while (resultSet.next()) {
                // Phần lấy dữ liệu (M)
                String maPhong = resultSet.getString("Id");
                String tenPhong = resultSet.getString("Name");
                String motaPhong = resultSet.getString("Description");

                // Phần hiển thị dữ liệu (V), VI PHẠM MÔ HÌNH MVC
                System.out.println(maPhong);
                System.out.println(tenPhong);
                System.out.println(motaPhong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        try (ResultSet resultSet = JdbcV2.executeQuery(stmSELECT)) {
            while (resultSet.next()) {
                Department dept = new Department();
                dept.setId(resultSet.getString("Id"));
                dept.setName(resultSet.getString("Name"));
                dept.setDescription(resultSet.getString("Description"));
                list.add(dept);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Department> getAll() {
        return findAll();
    }

    public Department findById(String id) {
        Department dept = null;
        try (java.sql.Connection conn = JdbcV2.getConnection();
             java.sql.ResultSet resultSet = JdbcV2.query(conn, stmSELECT_byId, id)) {
            if (resultSet.next()) {
                dept = new Department();
                dept.setId(resultSet.getString("Id"));
                dept.setName(resultSet.getString("Name"));
                dept.setDescription(resultSet.getString("Description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dept;
    }

    public Department getById(String id) {
        return findById(id);
    }

    public List<Department> findByName(String name) {
        List<Department> list = new ArrayList<>();
        try (java.sql.Connection conn = JdbcV2.getConnection();
             java.sql.ResultSet resultSet = JdbcV2.query(conn, stmSELECT_byName, '%' + name + '%')) {
            while (resultSet.next()) {
                Department dept = new Department();
                dept.setId(resultSet.getString("Id"));
                dept.setName(resultSet.getString("Name"));
                dept.setDescription(resultSet.getString("Description"));
                list.add(dept);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. THÊM MỚI - Sử dụng executeUpdate
    public int insert(Department dept) {
        try {
            return JdbcV3.executeUpdate(callINSERT, dept.getId(), dept.getName(), dept.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 4. CẬP NHẬT - Sử dụng executeUpdate
    public int update(Department dept) {
        try {
            return JdbcV3.executeUpdate(callUPDATE, dept.getId(), dept.getName(), dept.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 5. XÓA - Sử dụng executeUpdate
    public int delete(String id) {
        try {
            return JdbcV3.executeUpdate(callDELETE_byId, id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
