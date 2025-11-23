package ru.jabki.filmplus.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {

    private Long id;
    private Long filmId;
    private Long userId;
    private String content;
}
