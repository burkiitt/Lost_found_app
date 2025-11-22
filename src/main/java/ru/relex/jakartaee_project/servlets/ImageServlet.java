package ru.relex.jakartaee_project.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    // Папка, куда ты сохраняешь фото
    private static final String UPLOAD_DIR = "D:/idea_java_projects/JakartaEE_project/uploads";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String pathInfo = req.getPathInfo(); // /5/img1.jpeg

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Полный путь к файлу
        File file = new File(UPLOAD_DIR + pathInfo);

        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // MIME type (чтобы браузер понимал что это за файл)
        String mimeType = getServletContext().getMimeType(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        resp.setContentType(mimeType);
        resp.setContentLengthLong(file.length());

        // Отправка файла клиенту
        try (var output = resp.getOutputStream();
             var input = new FileInputStream(file)) {

            input.transferTo(output);
        }
    }
}
