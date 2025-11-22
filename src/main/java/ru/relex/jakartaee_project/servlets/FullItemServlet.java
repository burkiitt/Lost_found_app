package ru.relex.jakartaee_project.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.relex.jakartaee_project.dto.FullItemDto;
import ru.relex.jakartaee_project.entity.Image;
import ru.relex.jakartaee_project.service.ItemService;

import java.io.IOException; import java.io.PrintWriter;

@WebServlet("/full_Item")
public class FullItemServlet extends HttpServlet {
    private final ItemService itemService = ItemService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idParam = req.getParameter("id");

        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
            return;
        }

        FullItemDto fullItemDto = itemService.findByIdFull(Long.parseLong(idParam));

        if (fullItemDto == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
            return;
        }

        req.setAttribute("item", fullItemDto);

        req.getRequestDispatcher("/WEB-INF/views/fullitem.jsp")
                .forward(req, resp);
    }

}