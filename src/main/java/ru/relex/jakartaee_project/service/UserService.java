package ru.relex.jakartaee_project.service;

import ru.relex.jakartaee_project.Mapper.CreateUserMapper;
import ru.relex.jakartaee_project.Mapper.UserMapper;
import ru.relex.jakartaee_project.dao.UserDao;
import ru.relex.jakartaee_project.dto.CreateUserDto;
import ru.relex.jakartaee_project.dto.UserDto;
import ru.relex.jakartaee_project.exception.ValidationException;
import ru.relex.jakartaee_project.validator.CreateUserValidator;
import ru.relex.jakartaee_project.validator.Error;

public class UserService {
    public static final UserService INSTANCE = new UserService();
    public static final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    public static final UserDao userDao = UserDao.getInstance();
    public static final CreateUserValidator creatUservalidator = CreateUserValidator.getInstance();
    public static final UserMapper userMapper = UserMapper.getInstance();
    private UserService() {}

    public static UserService getInstance() {
        return INSTANCE;
    }
    public Long create(CreateUserDto createUserDto) {
        var validationResult = creatUservalidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        if (userDao.findByEmail(createUserDto.getEmail()) != null) {
            validationResult.addError(Error.of("duplicate.email", "Email already registered"));
            throw new ValidationException(validationResult.getErrors());
        }

        var user = createUserMapper.mapFrom(createUserDto);
        userDao.save(user);
        return  user.getId();
    }

    public UserDto login(String email, String password) {
        var user = userDao.findByEmail(email);
        return userMapper.mapFrom(user);
    }
}
