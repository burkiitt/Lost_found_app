package ru.relex.jakartaee_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.relex.jakartaee_project.service.CategoryService;
import ru.relex.jakartaee_project.service.ImageService;
import ru.relex.jakartaee_project.service.ItemService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/addItem")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,  // 5MB per file
        maxRequestSize = 1024 * 1024 * 20
)
public class AddItemServlet extends HttpServlet {

    private final ItemService itemService = ItemService.getInstance();
    private final CategoryService categoryService = CategoryService.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/add_item.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        long userId = (long) req.getSession().getAttribute("user_id");

        String type = req.getParameter("type");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String location = req.getParameter("location");
        String categoryName = req.getParameter("category_name");
        String eventDateStr = req.getParameter("event_date");

        LocalDateTime eventDate = LocalDateTime.parse(eventDateStr);

        // category ID from name
        long categoryId = categoryService.getCategoryIdByName(categoryName);

        // 1. Create item in DB
        long itemId = itemService.createItem(
                userId,
                categoryId,
                type,
                title,
                description,
                location,
                eventDate
        );

        // 2. Handle images
        List<Part> images = req.getParts().stream()
                .filter(p -> p.getName().equals("images") && p.getSize() > 0)
                .toList();

        if (images.size() > 3) {
            resp.sendError(400, "Maximum 3 images allowed.");
            return;
        }

        String uploadDirPath = getServletContext().getRealPath("/images/" + itemId + "/");
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        int counter = 1;
        for (Part part : images) {

            String filename = "img" + counter + ".jpeg";
            File file = new File(uploadDir, filename);

            part.write(file.getAbsolutePath());

            imageService.saveImage(itemId, filename);

            counter++;
        }

        resp.sendRedirect(req.getContextPath() + "/items");
    }
}
