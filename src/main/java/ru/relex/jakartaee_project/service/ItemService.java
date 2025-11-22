package ru.relex.jakartaee_project.service;

import ru.relex.jakartaee_project.dao.CategoryDao;
import ru.relex.jakartaee_project.dao.ImageDao;
import ru.relex.jakartaee_project.dao.ItemDao;
import ru.relex.jakartaee_project.dao.UserDao;
import ru.relex.jakartaee_project.dto.FullItemDto;
import ru.relex.jakartaee_project.dto.ItemDto;
import ru.relex.jakartaee_project.entity.Category;
import ru.relex.jakartaee_project.entity.Image;
import ru.relex.jakartaee_project.entity.Item;
import ru.relex.jakartaee_project.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ItemService {
    public static final ItemService INSTANCE = new ItemService();

    public static final ItemDao itemDao = ItemDao.getInstance();

    public static final ImageDao imageDao = ImageDao.getInstance();

    private ItemService() {}

    public static ItemService getInstance() {
        return INSTANCE;
    }

    public List<ItemDto> findAll() {
        return itemDao.findAll().stream().map(item ->
                new ItemDto(item.getId(), item.getTitle(),
                        item.getDescription(),
                        imageDao.findByItemID(item.getId()))).collect(Collectors.toList());
    }
    public FullItemDto findByIdFull(long id) {
        Item item = itemDao.findById(id);
        if (item == null) return null;

        User user = UserDao.getInstance().findById(item.getUser_id());
        Category category = CategoryDao.getInstance().findById(item.getCategory_id());
        List<Image> images = ImageDao.getInstance().findByItemID(item.getId());

        return new FullItemDto(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getLocation(),
                item.getEvent_date(),
                item.getStatus(),
                user,
                category,
                images
        );
    }
    public long createItem(long userId, long categoryId, String type,
                           String title, String desc, String location,
                           LocalDateTime eventDate) {
        Item item = new Item(userId, categoryId, type, title, desc, location, eventDate);
        return itemDao.save_return_id(item);
    }
}