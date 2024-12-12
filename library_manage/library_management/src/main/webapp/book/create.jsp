<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style><%@include file="/style/createBook.css"%></style>
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
        <div class="col-12 col-md-2 sidebar" id="sidebar">
            <div class="menu-title">MENU</div>
            <a href="/borrowInfos?action=create&page=1&size=10" ><i class="fas fa-book"></i> Mượn Sách</a>
            <a href="/books?page=1&size=10" class="manage-books active"><i class="fas fa-cogs"></i> Quản lý sách</a>
            <a href="/borrowInfos?page=1&size=10"><i class="fas fa-users"></i> Quản lý người mượn</a>
        </div>

        <!-- Main Content -->

        <div class="col content">
            <div class="form-container">
                <div class="form-title">Thông tin sách</div>
                <form action="/books?action=create" method="post">
                    <div class="row mt-3">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="book-title" class="form-label">Tên sách</label>
                                <input type="text" name="title" id="title" class="form-control" id="book-title" placeholder="Nhập tên sách" required>
                            </div>
                            <div class="mb-3">
                                <label for="author" class="form-label">Tác giả</label>
                                <input type="text" name="author" id="author" class="form-control" placeholder="Nhập tên tác giả" required>
                            </div>
                            <div class="mb-3">
                                <label for="isbn" class="form-label">Mã sách</label>
                                <input type="text" name="isbn" id="isbn"  class="form-control" placeholder="Nhập mã sách" required>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="total" class="form-label">Số sách</label>
                                <input type="number" name="total" id="total" class="form-control" placeholder="Nhập số lượng" required>
                            </div>
                            <div class="mb-3">
                                <label for="multiselect" class="form-label">Thể loại</label>
                                <br/>
                                <select id="multiselect" multiple style="width: 100%;" name="type" class="form-control" required>
                                    <c:forEach items="${types}" var="type" varStatus="status">
                                        <option value="${type.id}"> ${type.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="location" class="form-label">Vị trí trên kệ</label>
                                <input type="text" name="location" id="location"  placeholder="Vị trí trên kệ" class="form-control" required >
                            </div>
                        </div>
                    </div>
                    <div class="d-flex justify-content-end mt-3">
                        <button type="submit" class="btn btn-primary btn-custom me-2">Đồng ý</button>
                        <button type="reset" class="btn btn-secondary btn-custom">
                            <a href="/books?page=1&size=10" style="color: #fff; text-underline: none">
                                Hủy bỏ
                            </a>
                        </button>
                    </div>
                </form>
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
        gravity: "top", // 'top' hoặc 'bottom'
        position: "center", // 'left', 'center' hoặc 'right'
        backgroundColor: "#ba0517",
        color:"#ffffff"
    }).showToast();
</script>
<%
    }
%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/select2.min.js"></script>

<script>
    $(document).ready(function() {
        $('#multiselect').select2({
            placeholder: "Chọn loại sách",
            allowClear: true
        });
    });

</script>
</body>
</html>
