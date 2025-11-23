package ru.relex.jakartaee_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.relex.jakartaee_project.dao.ItemDao;
import ru.relex.jakartaee_project.dto.ItemDto;
import ru.relex.jakartaee_project.service.ItemService;

import java.io.IOException;
import java.util.List;

@WebServlet("/SearchItemsServlet")
public class SearchItemsServlet extends HttpServlet {

    ItemService itemService = ItemService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("name");
        String category = request.getParameter("category_name");
        String type = request.getParameter("type");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");


        List<ItemDto> items = itemService.searchItems(name, category, type, dateFrom, dateTo);

        request.setAttribute("items", items);
        request.getRequestDispatcher("WEB-INF/views/items.jsp").forward(request, response);
    }
}

