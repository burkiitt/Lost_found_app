package ru.relex.jakartaee_project.dto;

import ru.relex.jakartaee_project.entity.Category;
import ru.relex.jakartaee_project.entity.Image;
import ru.relex.jakartaee_project.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public record FullItemDto(long id,
                          String title,
                          String description,
                          String location,
                          LocalDateTime eventDate,
                          String status,
                          User user,
                          Category category,
                          List<Image> images) {
}
