package ru.relex.jakartaee_project.video;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.util.Map;

@WebServlet("/hello-video")
public class Hello extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name = req.getParameter("name");
//        var map = req.getParameterMap();
//        resp.setContentType("text/html,charset=utf-8");
//        PrintWriter out = resp.getWriter();
//        out.println("Hello World");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var s = req.getReader();
            var lines = s.lines();
        ){
            lines.forEach(System.out::println);
        }   
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
