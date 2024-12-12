<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous"
    >
    <link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style><%@include file="/style/borrowList.css"%></style>
</head>
<body>
<div class="header">
    <div>
        <button class="btn btn-sm btn-light d-md-none" id="menu-toggle">
            <i class="fas fa-bars"></i>
        </button>
        LHT-LIBRARY - MANAGEMENT
    </div>
    <div>
        <a href="/auth?action=logout">
            <i class="fa-solid fa-arrow-right-from-bracket"></i>
        </a>
    </div>
</div>
<div class="col-2 sidebar" id="sidebar">
    <div class="menu-title">MENU</div>
    <a href="/borrowInfos?action=create&page=1&size=10">
        <i class="fas fa-book"></i> Mượn Sách
    </a>
    <a href="/books?page=1&size=10">
        <i class="fas fa-cogs"></i> Quản lý sách
    </a>
    <a href="/borrowInfos?page=1&size=10" class="active">
        <i class="fas fa-users"></i> Quản lý người mượn
    </a>
</div>
<form action="/borrowInfos?action=search&page=1&size=10" method="post">

    <div class="search-container">
        <div class="search-bar">
                <input type="text" name="inputSearch" placeholder="Nhập mã sách hoặc tên sách" class="search-input" />
                <button class="search-button" type="submit">
                    <img src="/images/search.png" alt="Tìm kiếm" class="search-logo">
                </button>
        </div>
    </div>
</form>

<div class="table-wrapper">
    <table>
        <thead>
        <tr>
            <th scope="col">STT</th>
            <th scope="col">Mã Sách</th>
            <th scope="col">Tên Sách</th>
            <th scope="col">Tên Sinh Viên</th>
            <th scope="col">Mã Sinh Viên</th>
            <th scope="col">Trạng Thái</th>
            <th scope="col">Ngày Mượn</th>
            <th scope="col">Ngày Trả</th>
            <th scope="col">Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="borrowInfo" varStatus="s">
            <tr>
                <td> ${s.count}</td>
                <td> ${borrowInfo.book.isbn}</td>
                <td> ${borrowInfo.book.title}</td>
                <td> ${borrowInfo.studentName}</td>
                <td> ${borrowInfo.studentCode}</td>
                <td>
                <c:if test="${borrowInfo.status}">
                    Đang Mượn
                </c:if>
                    <c:if test="${!borrowInfo.status}">
                        Đã Trả
                    </c:if>
                </td>
                <td> <fmt:formatDate value="${borrowInfo.borrowDate}" pattern="dd/MM/yyyy"></fmt:formatDate> </td>
                <td> <fmt:formatDate value="${borrowInfo.dueDate}" pattern="dd/MM/yyyy"></fmt:formatDate> </td>
                <td>
                    <c:if test="${borrowInfo.status}">
                        <button class="action-button edit-button">
                            <a href="/borrowInfos?action=restore&id=${borrowInfo.id}">
                                <img src="/images/ift.png" alt="Chỉnh sửa" class="icon">
                            </a>
                        </button>
                    </c:if>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
<script src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
<script>
    Toastify({
        text: "<%= msg %>",
        duration: 3000,
        gravity: "top",
        position: "center",
        backgroundColor: "#ba0517",
        color:"#ffffff"
    }).showToast();
</script>
<%
    }
%>
    <div class="pagination-container">
        <div class="pagination">
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:if test="${i == currentPage}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <p class="page-link">${i}</p>
                </li>
            </c:if>
            <c:if test="${i != currentPage}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <c:if test="${key == 'search'}">
                        <form action="/borrowInfos?action=search&page=${i}&size=10" method="post">
                            <input type="hidden" name="inputSearch" class="search-input" />
                            <button class="page-link" type="submit">
                                    ${i}
                            </button>
                        </form>
                    </c:if>
                    <c:if test="${key != 'search'}">
                        <a class="page-link" href="/borrowInfos?page=${i}&size=10">${i}</a>
                    </c:if>
                </li>
            </c:if>
        </c:forEach>
        </div>
    </div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
        integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous">
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js"
        integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V" crossorigin="anonymous">
</script>

</body>
</html>
