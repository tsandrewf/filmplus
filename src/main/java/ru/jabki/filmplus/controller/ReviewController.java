package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.jabki.filmplus.model.Review;
import ru.jabki.filmplus.service.ReviewService;

@RestController
@RequestMapping("/api/v1/review")
@Tag(name = "Отзывы")
public class ReviewController {

    public final ReviewService reviewService;

    public ReviewController(final ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @Operation(summary = "Создать отзыв")
    public Review create(@RequestBody final Review review) {
        return reviewService.create(review);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить отзыв по id")
    public Review getById(@PathVariable("id") Long id) {
        return reviewService.getById(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление содержимого отзыва")
    public Review update(@RequestBody final Review review) {
        return reviewService.update(review);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отзыв по id")
    public void delete(@PathVariable("id") Long id) {
        reviewService.delete(id);
    }
}
