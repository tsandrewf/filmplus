package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.exception.UserNotFoundException;
import ru.jabki.filmplus.model.User;

@Service
public class UserService {

    private static final Set<User> users = new HashSet<>();

    public User create(final User user) {
        validate(user);
        user.setId((long)(users.size() + 1));
        users.add(user);
        return user;
    }

    public User getById(final long id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    public User update(final User user) {
        validate(user);
        final User existUser = getById(user.getId());
        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        existUser.setLogin(user.getLogin());
        existUser.setBirthday(user.getBirthday());
        return existUser;
    }

    public void delete(final Long id) {
        users.remove(getById(id));
    }

    private void validate(final User user) {
        if (user == null) {
            throw new UserException("Пользователь не задан");
        }
        if (!StringUtils.hasText(user.getName())) {
            throw new UserException("Имя пользователя не задано");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new UserException("Email пользователя не задан");
        }
        if (!StringUtils.hasText(user.getLogin())) {
            throw new UserException("Login пользователя не задан");
        }
        if (user.getBirthday() == null) {
            throw new UserException("День рождения пользователя не задан");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new UserException("День рождения пользователя больше текущей даты");
        }
    }
}
