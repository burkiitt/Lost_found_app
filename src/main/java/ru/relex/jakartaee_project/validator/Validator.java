package ru.relex.jakartaee_project.validator;

public interface Validator <T>{
    public ValidationResult isValid(T t);
}
