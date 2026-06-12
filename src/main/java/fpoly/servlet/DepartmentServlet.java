package fpoly.servlet;

import Dao.DepartmentDAO;
import entity.Department;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/department/*")
public class DepartmentServlet extends HttpServlet {
    private DepartmentDAO dao = new DepartmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        // Nếu không có pathInfo (ví dụ truy cập /department)
        if (pathInfo == null || pathInfo.equals("/")) {
            List<Department> list = dao.getAll();
            req.setAttribute("departments", list);
            req.getRequestDispatcher("/view_dept/department-list.jsp").forward(req, resp);
            return;
        }

        // Tách pathInfo: /name/BanGiámĐốc -> ["", "name", "BanGiámĐốc"]
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length >= 3) {
            String fieldName = pathParts[1];
            String keyword = pathParts[2];
            
            List<Department> list = new ArrayList<>();
            switch (fieldName.toLowerCase()) {
                case "id":
                    Department d = dao.findById(keyword);
                    if (d != null) list.add(d);
                    break;
                case "name":
                    list = dao.findByName(keyword);
                    break;
                default:
                    list = dao.getAll();
                    break;
            }
            req.setAttribute("departments", list);
            req.getRequestDispatcher("/view_dept/department-list.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/department/");
        }
    }
}
