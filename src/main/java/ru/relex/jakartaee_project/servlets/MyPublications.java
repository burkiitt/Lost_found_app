package ru.relex.jakartaee_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.relex.jakartaee_project.dto.ItemDto;
import ru.relex.jakartaee_project.dto.UserDto;
import ru.relex.jakartaee_project.entity.Item;
import ru.relex.jakartaee_project.service.ItemService;

import java.io.IOException;
import java.util.List;
import java.util.function.LongFunction;

@WebServlet("/publications")
public class MyPublications extends HttpServlet {
    ItemService itemService = ItemService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id  = (Long)req.getSession().getAttribute("user_id");
        List<ItemDto> itemList= itemService.findItemsByUserId(id);
        req.setAttribute("itemList", itemList);
        req.getRequestDispatcher("/WEB-INF/views/publications.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemIdParam = req.getParameter("itemId");
        if (itemIdParam != null && !itemIdParam.isEmpty()) {
            try {
                Long itemId = Long.parseLong(itemIdParam);
                Long userId = (Long) req.getSession().getAttribute("user_id");
                
                // Verify that the item belongs to the current user before deleting
                List<ItemDto> userItems = itemService.findItemsByUserId(userId);
                boolean itemBelongsToUser = userItems.stream()
                    .anyMatch(item -> item.id()==itemId);
                
                if (itemBelongsToUser) {
                    itemService.deleteItem(itemId);
                }
            } catch (NumberFormatException e) {
                // Invalid item ID, ignore
            }
        }
        
        // Redirect back to publications page after deletion
        resp.sendRedirect(req.getContextPath() + "/publications");
    }
}
