package ru.relex.jakartaee_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.relex.jakartaee_project.dto.CreateUserDto;
import ru.relex.jakartaee_project.dto.UserDto;
import ru.relex.jakartaee_project.exception.ValidationException;
import ru.relex.jakartaee_project.service.UserService;

import java.io.IOException;
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    public static final  UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = CreateUserDto.builder()
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
        try {
            userService.create(userDto);
            resp.sendRedirect("/login");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            System.out.println(e.getMessage());
            doGet(req, resp);
        }
    }
}
