package ru.relex.jakartaee_project.Mapper;

import ru.relex.jakartaee_project.dto.CreateUserDto;
import ru.relex.jakartaee_project.entity.Role;
import ru.relex.jakartaee_project.entity.User;

import java.time.LocalDateTime;

public class CreateUserMapper implements Mapper<User, CreateUserDto> {
    public static final CreateUserMapper INSTANCE = new CreateUserMapper();
    private CreateUserMapper() {}
    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .name(createUserDto.getName())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .role(Role.USER)
                .build();
    }
}
