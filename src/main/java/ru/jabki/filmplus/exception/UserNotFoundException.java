package ru.jabki.filmplus.exception;

public class UserNotFoundException extends UserException {

    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
