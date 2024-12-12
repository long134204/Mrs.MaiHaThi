
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
    <style><%@include file="/style/restore.css"%></style>
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
            <a href="/borrowInfos?action=create&page=1&size=10"><img src="/images/bookop.png" alt="trangsach" class="icon">Trang Sách</a>

            <a href="/books?page=1&size=10"><img src="/images/book.png" alt="quanlysach" class="icon">Quản Lý Sách</a>

            <a href="/borrowInfos?page=1&size=10" class="active"><img src="/images/user.png" alt="quanlynguoi" class="icon">Quản Lý Người Mượn</a>
        </div>

        <!-- Content -->
        <div class="col content">
            <form action="/borrowInfos?action=restore" method="post">
                <div class="form-container">
                    <div class="form-title">Thông tin người mượn</div>
                    <div class="row mt-4">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <input type="hidden" class="form-control" name="borrowInfoId" value="${borrowInfo.id}">
                                <label class="form-label">Tên người mượn</label>
                                <input type="text" class="form-control" value="${borrowInfo.studentName}" readonly>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Mã SV</label>
                                <input type="text" class="form-control" value="${borrowInfo.studentCode}" readonly>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label">Ngày mượn</label>
                                <input type="date" class="form-control" value="${borrowInfo.borrowDate}" readonly>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Ngày trả</label>
                                <input type="date" class="form-control" value="${borrowInfo.dueDate}" readonly>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive mt-4">
                        <table class="table table-bordered">
                            <thead>
                            <tr class="table-secondary">
                                <th>Mã sách</th>
                                <th>Tên sách</th>
                                <th>Vị trí sách</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${borrowInfo.book.isbn}</td>
                                <td>${borrowInfo.book.title}</td>
                                <td>${borrowInfo.book.location}</td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                    <div class="text-center">
                        <button class="btn btn-primary btn-custom" type="submit">TRẢ SÁCH</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
