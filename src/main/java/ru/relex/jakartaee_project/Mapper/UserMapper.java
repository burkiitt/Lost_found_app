package ru.relex.jakartaee_project.Mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.relex.jakartaee_project.dto.UserDto;
import ru.relex.jakartaee_project.entity.User;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<UserDto,User >{
    public static final UserMapper INSTANCE = new UserMapper();

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreated_at())
                .build();
    }
}
