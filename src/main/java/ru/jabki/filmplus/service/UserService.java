package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.exception.UserNotFoundException;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public User create(final User user) {
        validate(user);
        return userRepository.insert(user);
    }

    @Transactional(readOnly = true)
    public User getById(final long id) {
        final User user = userRepository.getById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    public User update(final User user) {
        validate(user);
        final User existUser = getById(user.getId());
        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        existUser.setLogin(user.getLogin());
        existUser.setBirthday(user.getBirthday());
        userRepository.update(existUser);
        return existUser;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(final Long id) {
        userRepository.delete(id);
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
