<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${department == null ? 'Thêm Phòng Ban' : 'Sửa Phòng Ban'}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">${department == null ? '➕ Thêm Phòng Ban Mới' : '✏️ Sửa Phòng Ban'}</h4>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/departments" method="post">
                        <input type="hidden" name="action" value="${department == null ? 'insert' : 'update'}">
                        
                        <div class="form-group">
                            <label for="id">Mã Phòng Ban</label>
                            <input type="text" class="form-group form-control" id="id" name="id" value="${department.id}" ${department != null ? 'readonly' : 'required'}>
                        </div>
                        
                        <div class="form-group">
                            <label for="name">Tên Phòng Ban</label>
                            <input type="text" class="form-group form-control" id="name" name="name" value="${department.name}" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="description">Mô Tả</label>
                            <textarea class="form-group form-control" id="description" name="description" rows="3">${department.description}</textarea>
                        </div>
                        
                        <div class="text-right">
                            <a href="${pageContext.request.contextPath}/departments" class="btn btn-secondary">Quay Lại</a>
                            <button type="submit" class="btn btn-primary">${department == null ? 'Thêm Mới' : 'Lưu Thay Đổi'}</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
