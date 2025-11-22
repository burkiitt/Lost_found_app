package ru.relex.jakartaee_project.service;

import ru.relex.jakartaee_project.dao.ImageDao;
import ru.relex.jakartaee_project.entity.Image;

public class ImageService {

    private static final ImageService instance = new ImageService();
    private final ImageDao imageDao = ImageDao.getInstance();

    public static ImageService getInstance() {
        return instance;
    }

    public void saveImage(long itemId, String fileName) {
        Image image = new Image(itemId, fileName);
        imageDao.save_return_id(image);
    }
}

