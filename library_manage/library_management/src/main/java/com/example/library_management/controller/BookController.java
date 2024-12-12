package com.example.library_management.controller;

import com.example.library_management.model.Book;
import com.example.library_management.model.BookType;
import com.example.library_management.model.BorrowInfo;
import com.example.library_management.model.Type;
import com.example.library_management.service.IBookService;
import com.example.library_management.service.IBookTypeService;
import com.example.library_management.service.ITypeService;
import com.example.library_management.service.impl.BookServiceImpl;
import com.example.library_management.service.impl.BookTypeServiceImpl;
import com.example.library_management.service.impl.TypeServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "bookController", value= "/books")
public class BookController extends HttpServlet {
    private IBookService bookService = new BookServiceImpl();
    private ITypeService typeService = new TypeServiceImpl();
    private IBookTypeService bookTypeService = new BookTypeServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session= req.getSession();
        String email = (String) session.getAttribute("email");
        if (email == null|| email == "" ){
            resp.sendRedirect("/auth/login.jsp");
            return;
        }
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                createForm(req,resp);
                break;
            case "update":
                updateForm(req,resp);
                break;
            default:
                listBook(req, resp);
        }
    }

    private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = bookService.findById(id);
        if (book == null){
            req.setAttribute("msg", "Không tìm thấy sách");
            resp.sendRedirect("/books?page=1&size=5");
        } else {
            List<Type> types = typeService.findAll();
            req.setAttribute("book", book);
            req.setAttribute("types", types);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/book/update.jsp");
            try {
                requestDispatcher.forward(req, resp);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void listBook(HttpServletRequest req, HttpServletResponse resp) {
        int page = Integer.parseInt(req.getParameter("page"));
        int size = Integer.parseInt(req.getParameter("size"));
        List<Book> list = bookService.findAllPaging(page, size);
        int totalRecord = bookService.countRecord();
        int totalPage = (int) Math.ceil((double) totalRecord/size);
        req.setAttribute("list", list);
        req.setAttribute("totalPages", totalPage);
        req.setAttribute("currentPage", page);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/book/list.jsp");
        try {
            requestDispatcher.forward(req,resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createForm(HttpServletRequest req, HttpServletResponse resp) {
        List<Type> types = typeService.findAll();
        req.setAttribute("types", types);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/book/create.jsp");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                create(req,resp);
                break;
            case "update":
                update(req,resp);
                break;
            case "delete":
                delete(req,resp);
                break;
            case "search":
                search(req, resp);
                break;
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inputSearch = req.getParameter("inputSearch");
        if (inputSearch.isEmpty()){
            resp.sendRedirect("/books?page=1&size=10");
        } else {
            int page = Integer.parseInt(req.getParameter("page"));
            int size = Integer.parseInt(req.getParameter("size"));
            List<Book> listSearch = bookService.search(inputSearch, page, size);
            int totalPage = (int) Math.ceil((double) listSearch.size()/size);
            req.setAttribute("totalPages", totalPage);
            req.setAttribute("currentPage", page);
            req.setAttribute("list", listSearch);
            req.setAttribute("key","search");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/book/list.jsp");
            try {
                requestDispatcher.forward(req,resp);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        if (bookService.delete(id)) {
            req.setAttribute("msg", "Xóa Thành Công");
            resp.sendRedirect("/books?page=1&size=10");
        } else {
            req.setAttribute("message", "Xóa Không Thành Công");
            resp.sendRedirect("/books?page=1&size=10");
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String isbn = req.getParameter("isbn");
        int total = Integer.parseInt(req.getParameter("total"));
        int available = Integer.parseInt(req.getParameter("available"));
        String location = req.getParameter("location");
        String[] typesId = req.getParameterValues("type");
        if (title == "" || author == "" || isbn== "" || location ==""  ){
            Book book = bookService.findById(id);
            List<Type> types = typeService.findAll();
            req.setAttribute("book", book);
            req.setAttribute("types", types);
            req.setAttribute("msg","Vui lòng nhập đủ các trường");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/book/update.jsp");
            requestDispatcher.forward(req,resp);
        } else {
            Book book = new Book(id,title, author, isbn, available ,total, location);
            if (bookService.update(book)) {
                bookTypeService.delete(id);
                for (String typeId : typesId) {
                    bookTypeService.create( Integer.parseInt(typeId),id);
                }
                req.setAttribute("msg", "Cập Nhật Thành Công");
                resp.sendRedirect("/books?page=1&size=10");
            } else {
                List<Type> types = typeService.findAll();
                req.setAttribute("types", types);
                req.setAttribute("message", "Thêm Mới Thất Bại");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/book/create.jsp");
                requestDispatcher.forward(req,resp);
            }
        }

    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String isbn = req.getParameter("isbn");
        int total = Integer.parseInt(req.getParameter("total"));
        String location = req.getParameter("location");
        String[] typesId = req.getParameterValues("type");
        if (!bookService.checkIsbn(isbn)){
            List<Type> types = typeService.findAll();
            req.setAttribute("types", types);
            req.setAttribute("msg","Mã sách đã tồn tại");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/book/create.jsp");
            requestDispatcher.forward(req,resp);
        }else if (title == "" || author == "" || isbn== "" || location ==""  ){
            List<Type> types = typeService.findAll();
            req.setAttribute("types", types);
            req.setAttribute("msg","Vui lòng nhập đủ các trường");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/book/create.jsp");
            requestDispatcher.forward(req,resp);
        } else {
            Book book = new Book(title, author, isbn, total ,total, location);
            if (bookService.create(book)){
                int bookId = bookService.getIdBookByIsbn(isbn);
                for (String typeId : typesId) {
                    bookTypeService.create( Integer.parseInt(typeId),bookId);
                }

                req.setAttribute("msg", "Thêm thành công");
                resp.sendRedirect("/books?page=1&size=10");
            } else {
                List<Type> types = typeService.findAll();
                req.setAttribute("types", types);
                req.setAttribute("message", "Thêm Mới Thất Bại");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/book/create.jsp");
                requestDispatcher.forward(req,resp);
            }
        }

    }
}
