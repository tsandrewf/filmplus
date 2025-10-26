package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Пользователи")
public class UserController {

    public final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public User create(@RequestBody final User user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление пользователя")
    public User update(@RequestBody final User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по id")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
