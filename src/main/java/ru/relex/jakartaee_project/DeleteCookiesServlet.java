package ru.relex.jakartaee_project;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
public class DeleteCookiesServlet extends HttpServlet {
    protected void  doPost(HttpServletRequest request, HttpServletResponse response){

    }
    protected void  doGet(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("some_id","");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
