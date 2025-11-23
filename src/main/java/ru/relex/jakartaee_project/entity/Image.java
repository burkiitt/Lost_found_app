package ru.relex.jakartaee_project.entity;

import java.time.LocalDateTime;

public class Image {
    private long id;
    private long item_id;
    private String url;

    // Конструктор для чтения из БД (все поля известны)
    public Image(long id, long item_id, String url) {
        this.id = id;
        this.item_id = item_id;
        this.url = url;
    }

    // Конструктор для создания нового Image (id + created_at задаст БД)
    public Image(long item_id, String url) {
        this.item_id = item_id;
        this.url = url;
    }

    public Image() {}

    // Getters / Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", item_id=" + item_id +
                ", url='" + url + '\'' +
                '}';
    }
}
