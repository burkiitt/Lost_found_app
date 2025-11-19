package ru.relex.jakartaee_project.exception;

import lombok.Getter;

import java.util.List;
import ru.relex.jakartaee_project.validator.Error;

public class ValidationException extends RuntimeException {
    @Getter
    private final List<Error> errors;
    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
