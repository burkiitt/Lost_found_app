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

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String idParam = req.getParameter("id");

        FullItemDto fullItemDto = itemService.findByIdFull(Long.parseLong(idParam));

        if (fullItemDto == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
            return;

        }
        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>Items Full</title></head><body>");

        out.println("<h2>"+fullItemDto.title()+"</h2>"); // и так далее
        out.println("<p>"+fullItemDto.description()+"</p>");
        out.println("<p>"+fullItemDto.location()+"</p>");
        out.println("<p>"+fullItemDto.eventDate()+"</p>");
        out.println("<p>"+fullItemDto.status()+"</p>");
        out.println("<p>"+fullItemDto.user()+"</p>");
        out.println("<p>"+fullItemDto.category()+"</p>");
        out.println("<b>Images:</b><br>");
        for (Image img : fullItemDto.images()) {
            out.println("<img src='" + img.getUrl() + "' width='200' style='margin:5px;'/>");
        }



        out.println("</body></html>");
    }
}