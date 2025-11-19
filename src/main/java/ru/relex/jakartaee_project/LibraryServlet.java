package ru.relex.jakartaee_project;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LibraryServlet extends HttpServlet {
    protected void  doPost(HttpServletRequest request, HttpServletResponse response){

    }
    protected void  doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try {
            Connection con  = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Books","postgres","Admin");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Books");
            while(rs.next()){
                out.println("<h1>"+rs.getString("title")+"</h1>");
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
