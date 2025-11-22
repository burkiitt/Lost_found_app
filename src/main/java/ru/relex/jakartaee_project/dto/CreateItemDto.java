package ru.relex.jakartaee_project.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateItemDto {
    String title;
    String description;
    String category;
    String type; // LOST / FOUND
    //Part image;  // файл
}
