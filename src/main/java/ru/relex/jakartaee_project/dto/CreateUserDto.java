package ru.relex.jakartaee_project.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;


@Value
@Builder
public class CreateUserDto {
    String name ;
    String email ;
    String password ;
}
