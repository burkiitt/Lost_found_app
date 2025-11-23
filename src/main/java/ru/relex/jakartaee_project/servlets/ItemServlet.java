package ru.relex.jakartaee_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.relex.jakartaee_project.dao.CategoryDao;
import ru.relex.jakartaee_project.dto.ItemDto;
import ru.relex.jakartaee_project.entity.Image;
import ru.relex.jakartaee_project.service.ItemService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/items")
public class ItemServlet extends HttpServlet {
    ItemService itemService = ItemService.getInstance();
    private final CategoryDao categoryDao = CategoryDao.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<ItemDto> items = itemService.findAll();
        List<String> categories = categoryDao.findAllNames();

        req.setAttribute("items", items);
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/WEB-INF/views/items.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
