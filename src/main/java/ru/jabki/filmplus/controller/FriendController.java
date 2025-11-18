package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.jabki.filmplus.model.Friend;
import ru.jabki.filmplus.service.FriendService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend")
@Tag(name = "Друзья")
public class FriendController {

    public final FriendService friendService;

    @PostMapping
    @Operation(summary = "Создать друга")
    public Friend create(@RequestBody final Friend friend) {
        return friendService.create(friend);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить друга по id")
    public Friend getById(@PathVariable("id") Long id) {
        return friendService.getById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить друга по id")
    public void delete(@PathVariable("id") Long id) {
        friendService.delete(id);
    }
}
