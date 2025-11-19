package ru.relex.jakartaee_project;

import ru.relex.jakartaee_project.dao.ItemDao;
import ru.relex.jakartaee_project.entity.Item;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

public class Runner {
    public static void main(String[] args) {
        ItemDao itemDao = ItemDao.getInstance();
        Item newItem = new Item(1L,1L,"lost","Iphone17","I lost it in cantin","Cantin", LocalDateTime.of(2025,11,15,10,30));
        System.out.println(itemDao.findById(2L));
    }
}
