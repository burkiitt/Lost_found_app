package ru.relex.jakartaee_project.validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    @Getter
    private final List<Error> errors = new ArrayList<Error>();
    public void addError(Error error) {
        this.errors.add(error);
    }
    public boolean isValid() {
        return errors.isEmpty();
    }
}
