package ru.relex.jakartaee_project.lost;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet(name = "AddItemServlet", value = "/add-item")
public class AddItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String itemName = request.getParameter("item_name");
        String place = request.getParameter("place");
        String description = request.getParameter("description");
        String timeStr = request.getParameter("time");

        Timestamp timestamp = null;
        if (timeStr != null && !timeStr.isEmpty()) {
            // datetime-local -> 2025-10-05T19:30 → Timestamp
            timeStr = timeStr.replace("T", " ");
            timestamp = Timestamp.valueOf(timeStr + ":00");
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO items (item_name, place, time, description) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, itemName);
            stmt.setString(2, place);
            stmt.setTimestamp(3, timestamp);
            stmt.setString(4, description);
            stmt.executeUpdate();

            response.getWriter().println("<h2>Item saved to database successfully!</h2>");
            response.getWriter().println("<a href='view-items'>View all items</a>");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}

