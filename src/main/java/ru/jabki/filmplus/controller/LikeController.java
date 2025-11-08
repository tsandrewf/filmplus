package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.jabki.filmplus.model.Like;
import ru.jabki.filmplus.service.LikeService;

@RestController
@RequestMapping("/api/v1/like")
@Tag(name = "Лайки")
public class LikeController {

    public final LikeService likeService;

    public LikeController(final LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    @Operation(summary = "Создать лайк")
    public Like create(@RequestBody final Like like) {
        return likeService.create(like);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить лайк по id")
    public Like getById(@PathVariable("id") Long id) {
        return likeService.getById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить лайк по id")
    public void delete(@PathVariable("id") Long id) {
        likeService.delete(id);
    }
}
