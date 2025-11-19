package ru.relex.jakartaee_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import ru.relex.jakartaee_project.dto.UserDto;
import ru.relex.jakartaee_project.service.UserService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public static final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if(email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            // Pass an error code for empty fields
            resp.sendRedirect("/login?error=empty&email=" + email);
            return;
        }
        UserDto userDto = userService.login(email, password);
        if(userDto != null) {
            onLoginSuccess(userDto, req, resp);
        } else {
            resp.sendRedirect("/login?error=invalid&email=" + email);
        }

    }
    @SneakyThrows
    private void onLoginSuccess(UserDto userDto, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", userDto);
        resp.sendRedirect("/items");
    }
}
