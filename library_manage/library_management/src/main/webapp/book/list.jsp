<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mvc" uri="http://jakarta.apache.org/taglibs/standard/permittedTaglibs" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Library Management</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous"
    >
    <link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style><%@include file="/style/list.css"%></style>
</head>
<body>
<!-- Header -->
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

<!-- Sidebar -->
<div class="sidebar" id="sidebar">
    <div class="menu-title">MENU</div>
    <a href="/borrowInfos?action=create&page=1&size=10">
        <i class="fas fa-book"></i> Mượn Sách
    </a>
    <a href="/books?page=1&size=10" class="active">
        <i class="fas fa-cogs"></i> Quản lý sách
    </a>
    <a href="/borrowInfos?page=1&size=10">
        <i class="fas fa-users"></i> Quản lý người mượn
    </a>
</div>


<div class="content">
    <form action="/books?action=search&page=1&size=10" method="post">
        <div class="search-container">
            <div class="search-bar">
                <input type="text" name="inputSearch" placeholder="Nhập mã sách hoặc tên sách" class="search-input">
                <button class="search-button" type="submit">
                    <img src="/images/search.png" alt="Tìm kiếm" class="search-logo">
                </button>
            </div>
        </div>
    </form>

    <button class="borrow-button">
        <a href="/books?action=create" style="color: #fff; text-decoration: none">Thêm mới</a>
    </button>

    <div class="table-wrapper">
        <table id="bookTable" >
            <thead>
            <tr>
                <th scope="col">STT</th>
                <th scope="col">Mã Sách</th>
                <th scope="col">Tên Sách</th>
                <th scope="col">Tác Giả</th>
                <th scope="col">Thể Loại</th>
                <th scope="col">Còn Lại</th>
                <th scope="col">Tổng</th>
                <th scope="col">Vị trí</th>
                <th scope="col">Thao tác</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${list}" var="book" varStatus="s">
                    <tr>
                        <td> ${s.count}</td>
                        <td> ${book.isbn}</td>
                        <td> ${book.title}</td>
                        <td> ${book.author}</td>
                        <td>
                            <c:forEach items="${book.typeBook}" var="type" varStatus="st">
                                <span>
                                    <c:if test="${st.last}">
                                        ${type.name}
                                    </c:if>
                                    <c:if test="${!st.last}">
                                        ${type.name},
                                    </c:if>
                                </span>
                            </c:forEach>
                        </td>
                        <td> ${book.available}</td>
                        <td> ${book.total}</td>
                        <td> ${book.location}</td>
                        <td>
                            <button class="action-button edit-button">
                                <a class="text-white" href="/books?action=update&id=${book.id}">
                                    <img src="/images/edit.png" alt="Chỉnh sửa" class="icon">
                                </a>
                            </button>
                            <button type="button" class="action-button delete-button" onclick="showDeleteModal(${book.id}, '${book.title}')">
                                <img src="/images/delete.png" alt="Xóa" class="icon">
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>


    <div class="pagination-container">
        <div class="pagination">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:if test="${i == currentPage}">
                    <li class="${i == currentPage ? 'active' : ''}">
                        <p class="page-link">${i}</p>
                    </li>
                </c:if>
                <c:if test="${i != currentPage}">
                    <li class="${i == currentPage ? 'active' : ''}">
                        <c:if test="${key == 'search'}">
                            <form action="/books?action=search&page=${i}&size=10">
                                <input type="text" name="inputSearch" placeholder="Nhập mã sách hoặc tên sách" class="search-input">
                                <button class="search-button" type="submit">
                                        ${i}
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${key != 'search'}">
                            <a class="page-link" href="/books?page=${i}&size=10">${i}</a>
                        </c:if>
                    </li>
                </c:if>
            </c:forEach>
        </div>
    </div>

    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <form action="/books?action=delete" method="post">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Xác nhận xóa</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="id" id="bookId" value="17">
                        Bạn có chắc chắn muốn xóa sách <span id="bookTitle" style="font-weight: bold"></span>?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-danger" id="confirmDeleteButton">Xóa</button>
                    </div>
                </div>
            </div>
        </form>
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
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
        integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous">
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js"
        integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V" crossorigin="anonymous">
</script>
<script>
    function showDeleteModal(bookId, bookTitle) {
        document.getElementById('bookTitle').textContent = bookTitle;
        document.getElementById('bookId').value = bookId;
        const confirmButton = document.getElementById('confirmDeleteButton');

        const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
        modal.show();

        confirmButton.onclick = function () {
            modal.hide();

        };
    }
</script>

</body>
</html>
