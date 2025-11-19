package ru.relex.jakartaee_project.validator;

import ru.relex.jakartaee_project.dto.CreateUserDto;
import ru.relex.jakartaee_project.entity.Role;
import ru.relex.jakartaee_project.entity.User;

import java.util.regex.Pattern;

public class CreateUserValidator implements Validator<CreateUserDto> {
    public static final CreateUserValidator INSTANCE = new CreateUserValidator();
    private static final Pattern EMAIL = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[A-Za-z]{2,}$");

    private CreateUserValidator() {}
    public static  CreateUserValidator getInstance() {
        return INSTANCE;
    }

    public ValidationResult isValid(CreateUserDto dto) {
        var result = new ValidationResult();

        String name = dto.getName();
        String email = dto.getEmail();
        String password = dto.getPassword();

        // name
        if (name == null || name.trim().isEmpty()) {
            result.addError(Error.of("empty.name", "Name is required"));
        }

        // email
        if (email == null || email.trim().isEmpty()) {
            result.addError(Error.of("empty.email", "Email is required"));
        } else if (!EMAIL.matcher(email).matches()) {
            result.addError(Error.of("invalid.email", "Email address is invalid"));
        }

        // password
        if (password == null || password.trim().isEmpty()) {
            result.addError(Error.of("empty.password", "Password is required"));
        } else if (password.length() < 4) {
            result.addError(Error.of("weak.password", "Password must be at least 6 characters"));
        }

        return result;
    }
}
