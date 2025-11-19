package ru.relex.jakartaee_project.Mapper;

public interface Mapper<T,F> {
    T mapFrom(F f);
}
