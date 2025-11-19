package ru.relex.jakartaee_project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime created_at;
}
