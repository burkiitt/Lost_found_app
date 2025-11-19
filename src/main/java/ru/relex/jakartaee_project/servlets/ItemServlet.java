package ru.relex.jakartaee_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.relex.jakartaee_project.dto.ItemDto;
import ru.relex.jakartaee_project.entity.Image;
import ru.relex.jakartaee_project.service.ItemService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/items")
public class ItemServlet extends HttpServlet {
    ItemService itemService = ItemService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        List<ItemDto> items = itemService.findAll();

        String context = req.getContextPath();

        out.println("<html><head><title>Items Test</title></head><body>");
        out.println("<h2>TEST ITEMS OUTPUT</h2>");
        out.println("<ul style='list-style:none;'>");

        for (ItemDto item : items) {
            out.println("<li style='border:1px solid #ccc; padding:10px; margin:10px;'>");
            out.println("<a href='" + context + "/full_Item?id=" + item.id() + "' style='text-decoration:none; color:inherit;'>");
            out.println("<b>Title:</b> " + item.title() + "<br>");
            out.println("<b>Description:</b> " + item.description() + "<br>");

// фото
            out.println("<b>Images:</b><br>");
            for (Image img : item.images()) {
                out.println("<img src='" + img.getUrl() + "' width='200' style='margin:5px;'/>");
            }

            out.println("</a>");
            out.println("</li>");

        }

        out.println("</ul>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
