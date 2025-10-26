package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.jabki.filmplus.model.Friend;
import ru.jabki.filmplus.model.Like;
import ru.jabki.filmplus.service.FriendService;
import ru.jabki.filmplus.service.ReviewService;

@RestController
@RequestMapping("/api/v1/friend")
@Tag(name = "Друзья")
public class FriendController {

    public final FriendService friendService;

    public FriendController(final FriendService friendService) {
        this.friendService = friendService;
    }

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
