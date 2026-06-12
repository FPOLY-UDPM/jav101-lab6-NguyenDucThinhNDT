<%-- Tự động chuyển hướng sang Servlet quản lý phòng ban --%>
<% response.sendRedirect(request.getContextPath() + "/departments"); %>

<html>
<head>
    <title>Redirecting...</title>
</head>
<body>
    <p>Đang chuyển hướng... Nếu không tự động chuyển, vui lòng nhấn 
       <a href="${pageContext.request.contextPath}/departments">vào đây</a>.
    </p>
</body>
</html>
