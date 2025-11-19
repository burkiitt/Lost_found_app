package ru.relex.jakartaee_project.video;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.util.Map;

@WebServlet("/down")
public class DownloadServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Disposition", "attachment; filename=test.txt");
        resp.setContentType("text/plain");
        try(PrintWriter out = resp.getWriter()) {
            out.println("Hello World");
        }
    }



    @Override
    public void destroy() {
        super.destroy();
    }
}
