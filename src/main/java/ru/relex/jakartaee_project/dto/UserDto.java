package ru.relex.jakartaee_project.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class UserDto {
    long id;
    String fullName;
    String email;
    String role;
    LocalDateTime createdAt;
}
