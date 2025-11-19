package ru.relex.jakartaee_project.entity;

import java.util.Arrays;

public enum Role {
    ADMIN,USER;

    public static Role find(String role) {
        return Arrays.stream(values()).filter(e -> e.name().equals(role)).findFirst().orElse(null);
    }
}
