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
    <style><%@include file="/style/confirmBorrow.css"%></style>
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
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-2 sidebar" id="sidebar">
            <div class="menu-title">MENU</div>

            <a href="/borrowInfos?action=create&page=1&size=10" class="active"><img src="/images/bookop.png" alt="trangsach" class="icon">Trang Sách</a>

            <a href="/books?page=1&size=10"><img src="/images/book.png" alt="quanlysach" class="icon">Quản Lý Sách</a>

            <a href="/borrowInfos?page=1&size=10" ><img src="/images/user.png" alt="quanlynguoi" class="icon">Quản Lý Người Mượn</a>
        </div>
        <div class="col content">
            <div class="row mt-4">
                <!-- Title for Borrower Information -->
                <h3 class="w-100 mb-4 text-center">Sách mượn</h3>

                <!-- Table Section -->
                <div class="col-lg-8 table-responsive">
                    <table id="bookTable" class="table table-striped">
                        <thead class="table-primary">
                        <tr>
                            <th>STT</th>
                            <th>Mã sách</th>
                            <th>Tên sách</th>
                            <th>Vị trí trên kệ</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${books}" var="book" varStatus="status">
                            <tr>
                                <th>${status.count}</th>
                                <td>${book.isbn}</td>
                                <td>${book.title}</td>
                                <td>${book.location}</td>
                                <td>
                                    <button class="btn btn-danger btn-sm" onclick="handleClearSession(${book.id})">
                                        <a href="/borrowInfos?action=delete&id=${book.isbn}">
                                            🗑️
                                        </a>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-lg-4">
                    <div class="form-section">
                        <form action="/borrowInfos?action=create" method="post">
                            <div class="mb-3">
                                <label for="studentName" class="form-label">Tên sinh viên</label>
                                <input type="text" id="studentName" name="studentName" class="form-control" placeholder="Nhập tên sinh viên">
                            </div>
                            <div class="mb-3">
                                <label for="studentCode" class="form-label">Mã sinh viên</label>
                                <input type="number" id="studentCode" name="studentCode" class="form-control" placeholder="Nhập mã sinh viên">
                            </div>
                            <div class="mb-3">
                                <label for="borrowDate" class="form-label">Ngày mượn</label>
                                <input type="date" id="borrowDate" value="${borrowDate}" name="borrowDate" class="form-control" placeholder="Nhập số lượng" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="dueDate" class="form-label">Ngày trả</label>
                                <input type="date" id="dueDate" name="dueDate" class="form-control" placeholder="Nhập vị trí sách">
                            </div>
                            <button type="submit" class="btn btn-primary btn-custom" onclick="handleSubmit()">Xác nhận</button>
                            <button type="reset" class="btn btn-secondary btn-custom">
                                <a href="/borrowInfos?action=create&page=1&size=10" style="color: #fff; text-decoration: none">
                                    Quay lại
                                </a>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    function handleClearSession(idBook) {
        const list = JSON.parse(sessionStorage.getItem("selectedBook")) || [];
        const filterList = list.filter(id => id !== idBook);
        sessionStorage.setItem("selectedBook", JSON.stringify(filterList));
    }

    function handleSubmit(){
        sessionStorage.clear();
    }

</script>
</body>
</html>
