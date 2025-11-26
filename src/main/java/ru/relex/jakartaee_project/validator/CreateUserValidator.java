package ru.relex.jakartaee_project.validator;

import ru.relex.jakartaee_project.dto.CreateUserDto;

import java.util.regex.Pattern;

public class CreateUserValidator implements Validator<CreateUserDto> {
    public static final CreateUserValidator INSTANCE = new CreateUserValidator();
    private static final Pattern EMAIL = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[A-Za-z]{2,}$");
    private static final Pattern UPPERCASE = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWERCASE = Pattern.compile(".*[a-z].*");
    private static final Pattern DIGIT = Pattern.compile(".*\\d.*");
    private static final Pattern SPECIAL = Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

    private CreateUserValidator() {}
    public static CreateUserValidator getInstance() {
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
        } else {
            if (password.length() < 6) {
                result.addError(Error.of("weak.password.length", "Password must be at least 6 characters"));
            }
            if (!UPPERCASE.matcher(password).matches()) {
                result.addError(Error.of("weak.password.uppercase", "Password must contain at least one uppercase letter"));
            }
            if (!LOWERCASE.matcher(password).matches()) {
                result.addError(Error.of("weak.password.lowercase", "Password must contain at least one lowercase letter"));
            }
            if (!DIGIT.matcher(password).matches()) {
                result.addError(Error.of("weak.password.digit", "Password must contain at least one number"));
            }
            if (!SPECIAL.matcher(password).matches()) {
                result.addError(Error.of("weak.password.special", "Password must contain at least one special character"));
            }
        }

        return result;
    }
}
