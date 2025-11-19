package ru.relex.jakartaee_project.dto;

import ru.relex.jakartaee_project.entity.Image;

import java.util.List;

public record ItemDto(long id , String title , String description , List<Image> images) {
}
