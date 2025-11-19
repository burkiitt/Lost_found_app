package ru.relex.jakartaee_project.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.relex.jakartaee_project.dto.UserDto;

import java.io.IOException;
@WebServlet("/admin")
public class UnSafeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         var user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
         if (user != null) {
             filterChain.doFilter(servletRequest, servletResponse);
         }
         else {
             ((HttpServletResponse) servletResponse).sendRedirect("/registration");
         }
    }
}
