package ru.relex.jakartaee_project.service;

import ru.relex.jakartaee_project.dao.CategoryDao;

import java.util.List;

public class CategoryService {
    private static final CategoryService instance = new CategoryService();
    private final CategoryDao categoryDao = CategoryDao.getInstance();
    private CategoryService() {}
    public static CategoryService getInstance() {
        return instance;
    }
    public long getCategoryIdByName(String name) {
        return categoryDao.findIdByName(name);
    }

    public List<String> getAllNames() {
        return categoryDao.findAllNames();
    }
}
