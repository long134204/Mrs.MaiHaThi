package com.example.library_management.controller;

import com.example.library_management.model.Book;
import com.example.library_management.model.BorrowInfo;
import com.example.library_management.service.IBookService;
import com.example.library_management.service.IBorrowInfoService;
import com.example.library_management.service.impl.BookServiceImpl;
import com.example.library_management.service.impl.BorrowInfoServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@WebServlet(name = "borrowInfoController", value = "/borrowInfos")
public class BorrowInfoController extends HttpServlet {
    private IBorrowInfoService borrowInfoService = new BorrowInfoServiceImpl();
    private IBookService bookService = new BookServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session= req.getSession();
        if (session.getAttribute("email") == null ){
            resp.sendRedirect("/auth/login.jsp");
            return;
        }
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
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
            case "delete":
                deleteForm(req,resp);
                break;
            case "restore":
                restoreForm(req,resp);
                break;
            default:
                listBorrowInfo(req, resp);
        }
    }

    private void restoreForm(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        BorrowInfo borrowInfo = borrowInfoService.getBorrowInfo(id);
        req.setAttribute("borrowInfo", borrowInfo);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/borrow/restore.jsp");
        try {
            requestDispatcher.forward(req,resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String isbn = req.getParameter("id");

        HttpSession session = req.getSession();
        List<Book> books = (List<Book>) session.getAttribute("selectedBooks");

        if (books != null && isbn != null) {
            books.removeIf(book -> isbn.equals(book.getIsbn()));

            session.setAttribute("selectedBooks", books);
        }

        req.setAttribute("books", books);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/borrow/confirmCreate.jsp");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmForm(HttpServletRequest req, HttpServletResponse resp) {
        String listBookId = req.getParameter("selectedBooks");

        if (listBookId != null && listBookId.startsWith("[") && listBookId.endsWith("]")) {
            listBookId = listBookId.substring(1, listBookId.length() - 1);
        }
        List<Book> books = bookService.getBookById(listBookId);
        HttpSession session = req.getSession();
        session.setAttribute("selectedBooks", books);
        LocalDate today = LocalDate.now();
        String borrowDate = today.toString();
        req.setAttribute("borrowDate", borrowDate);

        req.setAttribute("books",books);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/borrow/confirmCreate.jsp");
        try {
            requestDispatcher.forward(req,resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listBorrowInfo(HttpServletRequest req, HttpServletResponse resp) {
        int page = Integer.parseInt(req.getParameter("page"));
        int size = Integer.parseInt(req.getParameter("size"));
        int totalRecord = borrowInfoService.countRecord();
        int totalPage = (int)Math.ceil((double) totalRecord / size);
        List<BorrowInfo> list = borrowInfoService.findAllPaging(page, size);
        req.setAttribute("list", list);
        req.setAttribute("totalPages", totalPage);
        req.setAttribute("currentPage", page);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/borrow/list.jsp");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateForm(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void createForm(HttpServletRequest req, HttpServletResponse resp) {
        int page = Integer.parseInt(req.getParameter("page"));
        int size = Integer.parseInt(req.getParameter("size"));
        List<Book> listBook = bookService.findAllPaging(page, size);
        int totalRecord = bookService.countRecord();
        int totalPage = (int) Math.ceil((double) totalRecord/size);
        req.setAttribute("listBook", listBook);
        req.setAttribute("totalPages", totalPage);
        req.setAttribute("currentPage", page);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/borrow/create.jsp");
        try {
            requestDispatcher.forward(req,resp);
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
                borrow(req,resp);
                break;
            case "confirm":
                confirmForm(req,resp);
                break;
            case "restore":
                restore(req,resp);
                break;
            case "search":
                search(req,resp);
                break;
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inputSearch = req.getParameter("inputSearch");
        if (inputSearch.isEmpty()){
            resp.sendRedirect("/borrowInfos?page=1&size=10");
        } else {
            int page = Integer.parseInt(req.getParameter("page"));
            int size = Integer.parseInt(req.getParameter("size"));
            List<BorrowInfo> listSearch = borrowInfoService.search(inputSearch, page, size);
            int totalPage = (int) Math.ceil((double) listSearch.size()/size);
            req.setAttribute("totalPages", totalPage);
            req.setAttribute("currentPage", page);
            req.setAttribute("list", listSearch);
            req.setAttribute("key","search");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/borrow/list.jsp");
            try {
                requestDispatcher.forward(req,resp);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("borrowInfoId"));
        if (borrowInfoService.restore(id)){
            Book book = borrowInfoService.getBorrowInfo(id).getBook();
            bookService.increaseAvailable(book.getId(), (book.getAvailable() +1));
            resp.sendRedirect("/borrowInfos?page=1&size=10");
        }
    }


    private void borrow(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String studentName = req.getParameter("studentName");
        String studentCode = req.getParameter("studentCode");
        String borrowDate = req.getParameter("borrowDate");
        String dueDate = req.getParameter("dueDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date formatBorrowDate = new Date();
        try {
            formatBorrowDate = simpleDateFormat.parse(borrowDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date formatDueDate = new Date();
        try {
            formatDueDate = simpleDateFormat.parse(dueDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HttpSession session = req.getSession();
        List<Book> books = (List<Book>) session.getAttribute("selectedBooks");
        if (books.size() == 0) {
            req.setAttribute("msg","Vui chọn sách để mượn");
            RequestDispatcher requestDispatcher =req.getRequestDispatcher("/borrow/confirmCreate.jsp");
            requestDispatcher.forward(req,resp);
        } else {
            for (int i = 0; i < books.size(); i++) {
                BorrowInfo borrowInfo = new BorrowInfo(studentCode, studentName, books.get(i),formatBorrowDate, formatDueDate, true);
                borrowInfoService.borrow(borrowInfo);
                bookService.decreaseQuantity(books.get(i).getId(), (books.get(i).getAvailable()-1));
            }
            resp.sendRedirect("/borrowInfos?page=1&size=10");
        }

    }
}
