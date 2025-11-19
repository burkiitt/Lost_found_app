package ru.relex.jakartaee_project;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

public class GetCookiesServlet extends HttpServlet {
    protected void  doPost(HttpServletRequest request, HttpServletResponse response){

    }
    protected void  doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        PrintWriter out = response.getWriter();
        out.println("<html>");
        for(Cookie cookie : cookies){
            out.println("<h1>"+cookie.getName()+":"+cookie.getValue()+"</h1>");
        }
        out.println("</html>");
    }
}
