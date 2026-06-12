<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Danh Sách Phòng Ban</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="container mt-5">

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2 class="text-primary m-0">QUẢN LÝ PHÒNG BAN</h2>
    <a href="${pageContext.request.contextPath}/departments?action=new" class="btn btn-success">➕ Thêm Phòng Ban Mới</a>
</div>

<table class="table table-bordered table-striped">
    <thead class="thead-dark">
    <tr>
        <th>Mã Phòng Ban</th>
        <th>Tên Phòng Ban</th>
        <th>Mô Tả</th>
        <th style="width: 180px;" class="text-center">Hành Động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="d" items="${departments}">
        <tr>
            <td><strong>${d.id}</strong></td>
            <td>${d.name}</td>
            <td>${d.description}</td>
            <td class="text-center">
                <a href="${pageContext.request.contextPath}/departments?action=edit&id=${d.id}" class="btn btn-warning btn-sm">✏️ Sửa</a>
                <a href="${pageContext.request.contextPath}/departments?action=delete&id=${d.id}"
                   class="btn btn-danger btn-sm"
                   onclick="return confirm('Thầy/Cô có chắc chắn muốn xóa phòng ban này không?');">🗑️ Xóa</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
