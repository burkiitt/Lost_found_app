package ru.relex.jakartaee_project.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Item {
    long id;
    long user_id;
    long category_id;
    String type;
    String title;
    String description;
    String location;
    LocalDateTime event_date;
    String status;
    LocalDateTime created_at;

    public Item() {}

    public Item(
            long user_id,
                long category_id,
                String type,
                String title,
                String description,
                String location,
                LocalDateTime event_date) {

        this.user_id = user_id;
        this.category_id = category_id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.location = location;
        this.event_date = event_date;

        this.status = "active"; // или "lost" или другое по умолчанию
        this.created_at = LocalDateTime.now();
    }

    public Item(
            long id,
            long user_id,
            long category_id,
            String type,
            String title,
            String description,
            String location,
            LocalDateTime event_date,
            String status,
            LocalDateTime created_at
    ) {
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.location = location;
        this.event_date = event_date;
        this.status = status;
        this.created_at = created_at;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getEvent_date() {
        return event_date;
    }

    public void setEvent_date(LocalDateTime event_date) {
        this.event_date = event_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && user_id == item.user_id && category_id == item.category_id && Objects.equals(type, item.type) && Objects.equals(title, item.title) && Objects.equals(description, item.description) && Objects.equals(location, item.location) && Objects.equals(event_date, item.event_date) && Objects.equals(status, item.status) && Objects.equals(created_at, item.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, category_id, type, title, description, location, event_date, status, created_at);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", category_id=" + category_id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", event_date=" + event_date +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
