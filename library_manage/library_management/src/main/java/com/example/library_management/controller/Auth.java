package com.example.library_management.controller;

import com.example.library_management.model.GoogleAccount;
import com.example.library_management.model.User;
import com.example.library_management.service.UserService;
import com.example.library_management.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "auth", value = "/auth")
public class Auth extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    private PasswordUtils passwordUtils = new PasswordUtils();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "register":
                register(req,resp);
            break;
            case "login":
                login(req,resp);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "logout":
                logout(req, resp);
                break;
            default:
                loginByEmail(req,resp);

        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("email");
        resp.sendRedirect("/auth/login.jsp");
    }

    private void loginByEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        GoogleLogin googleLogin = new GoogleLogin();
        String accessToken = googleLogin.getToken(code);
        GoogleAccount googleAccount = googleLogin.getUserInfo(accessToken);
        HttpSession session = req.getSession();
        session.setAttribute("email", googleAccount.getEmail());
        resp.sendRedirect("/books?page=1&size=10");
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email == "" || password == ""){
            req.setAttribute("msg","Vui lòng nhập đủ thông tin");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/auth/login.jsp");
            requestDispatcher.forward(req, resp);
        } else if (!userService.isExistEmail(email)) {
            req.setAttribute("msg","Email không tồn tại");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/auth/login.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            User user = new User(email, password);
            if (userService.login(user)) {
                HttpSession session = req.getSession();
                session.setAttribute("email", email);
                resp.sendRedirect("/books?page=1&size=10");
            } else {
                req.setAttribute("msg","Mật khẩu không đúng!");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/auth/login.jsp");
                requestDispatcher.forward(req, resp);
            }

        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email == "" || password == ""){
            req.setAttribute("msg","Vui lòng nhập đủ thông tin");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/auth/register.jsp");
            requestDispatcher.forward(req, resp);
        } else if (userService.isExistEmail(email)) {
            req.setAttribute("msg","Email đã tồn tại");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/auth/register.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            User user = new User(email, password);
            userService.register(user);
            HttpSession session= req.getSession();
            session.setAttribute("email",email);
            resp.sendRedirect("/books?page=1&size=10");
        }
    }
}
