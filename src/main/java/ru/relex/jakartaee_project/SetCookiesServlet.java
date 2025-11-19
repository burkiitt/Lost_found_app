package ru.relex.jakartaee_project;

import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
public class SetCookiesServlet extends HttpServlet {
    protected void  doPost(HttpServletRequest request, HttpServletResponse response){

    }
    protected void  doGet(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("some_id","123");
        Cookie cookie2 = new Cookie("some_name","Burkit");
        cookie.setMaxAge(24*60*60);
        cookie2.setMaxAge(24*60*60);
        response.addCookie(cookie);
        response.addCookie(cookie2);
    }
}
