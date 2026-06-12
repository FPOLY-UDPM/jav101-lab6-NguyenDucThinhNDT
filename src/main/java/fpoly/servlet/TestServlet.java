package fpoly.servlet;

import Dao.DepartmentDAO;
import entity.Department;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TestServlet extends HttpServlet {
    private DepartmentDAO dao = new DepartmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteDept(req, resp);
                break;
            default:
                listDepts(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("insert".equals(action)) {
            insertDept(req, resp);
        } else if ("update".equals(action)) {
            updateDept(req, resp);
        }
    }

    private void listDepts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Department> list = dao.getAll();
        req.setAttribute("departments", list);
        req.getRequestDispatcher("/view_dept/department-list.jsp").forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view_dept/department-form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Department existingDept = dao.getById(id);
        req.setAttribute("department", existingDept);
        req.getRequestDispatcher("/view_dept/department-form.jsp").forward(req, resp);
    }

    private void insertDept(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Department newDept = new Department(id, name, description);
        dao.insert(newDept);
        resp.sendRedirect(req.getContextPath() + "/departments");
    }

    private void updateDept(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Department dept = new Department(id, name, description);
        dao.update(dept);
        resp.sendRedirect(req.getContextPath() + "/departments");
    }

    private void deleteDept(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        dao.delete(id);
        resp.sendRedirect(req.getContextPath() + "/departments");
    }
}
