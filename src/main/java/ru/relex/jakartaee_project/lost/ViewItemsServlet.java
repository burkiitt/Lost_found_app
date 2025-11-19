package ru.relex.jakartaee_project.lost;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet(name = "ViewItemsServlet", value = "/view-items")
public class ViewItemsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM items ORDER BY id DESC")) {

            PrintWriter out = response.getWriter();
            out.println("<h2>📦 All Lost Items</h2>");
            out.println("<table border='1' cellpadding='8' cellspacing='0'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Place</th><th>Time</th><th>Description</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("item_name") + "</td>");
                out.println("<td>" + rs.getString("place") + "</td>");
                out.println("<td>" + rs.getTimestamp("time") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<br><a href='add-item.jsp'>➕ Add another item</a>");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<h2>❌ Error: " + e.getMessage() + "</h2>");
        }
    }
}
