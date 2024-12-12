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
    <style><%@include file="/style/createBorrow.css"%></style>
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
    <a href="/borrowInfos?action=create&page=1&size=10" class="active">
        <i class="fas fa-book"></i> Mượn Sách
    </a>
    <a href="/books?page=1&size=10" class="manage-books">
        <i class="fas fa-cogs"></i> Quản lý sách
    </a>
    <a href="/borrowInfos?page=1&size=10" >
        <i class="fas fa-users"></i> Quản lý người mượn
    </a>
</div>

<div class="table-wrapper">
    <form id="confirmForm" action="/borrowInfos?action=confirm" method="post">
        <input type="hidden" name="selectedBooks" id="selectedBooksInput" />
<%--        <button type="button" onclick="handleConfirm()">Tiếp tục</button>--%>
        <button class="borrow-button" id="btn-next" onclick="handleConfirm()">Tiếp tục</button>
    </form>

    <table id="bookTable" class="table table-striped">
        <thead>
        <tr>
            <th scope="col">STT</th>
            <th scope="col">Mã Sách</th>
            <th scope="col">Tên Sách</th>
            <th scope="col">Thể Loại</th>
            <th scope="col">Còn Lại</th>
            <th scope="col">Vị trí</th>
            <th scope="col">Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listBook}" var="book" varStatus="s">
            <tr>
                <td> ${s.count}</td>
                <td> ${book.isbn}</td>
                <td> ${book.title}</td>
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
                <td> ${book.location}</td>
                <td>
                    <button class="btn btn-primary" onclick="handleAdd(${book.id})">
                        <i class="fa-solid fa-plus" id="plus-icon-${book.id}" data-book-id="${book.id}"></i>
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
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <p class="page-link">${i}</p>
                </li>
            </c:if>
            <c:if test="${i != currentPage}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="/borrowInfos?action=create&page=${i}&size=10">${i}</a>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    let selected = sessionStorage.getItem("selectedBook")
        ? JSON.parse(sessionStorage.getItem("selectedBook"))
        : [];
   function check(){
       if (selected.length == 0){
           document.getElementById("btn-next").style.display = "none";
       } else {
           document.getElementById("btn-next").style.display = "block";
       }
   }
    check()
    function handleAdd(bookId) {
        const index = selected.indexOf(bookId);

        if (index === -1) {
            selected.push(bookId);
            check();
        } else {
            selected.splice(index, 1);
            check()
        }

        sessionStorage.setItem("selectedBook", JSON.stringify(selected));
        updateButtonIcon(bookId);
    }

    function updateButtonIcon(bookId) {
        const icon = document.querySelector(`#plus-icon-`+bookId);
            if (selected.includes(bookId)) {
                icon.classList.remove("fa-plus");
                icon.classList.add("fa-check");
            } else {
                icon.classList.remove("fa-check");
                icon.classList.add("fa-plus");
            }

    }

    function init() {
        const icons = document.querySelectorAll("[id^='plus-icon-']");
        icons.forEach((icon) => {
            const bookId = parseInt(icon.getAttribute("data-book-id"));
            updateButtonIcon(bookId);
        });
    }
    window.onload = init;
    function handleConfirm() {
        document.getElementById("selectedBooksInput").value = JSON.stringify(selected);
        document.getElementById("confirmForm").submit();
    }

</script>

</body>
</html>
