package ru.jabki.filmplus.model;

public class Review {

    private Long id;
    private Long filmId;
    private Long userId;
    private String content;

    public Review(Long id, Long filmId, Long userId, String content) {
        this.id = id;
        this.filmId = filmId;
        this.userId = userId;
        this.content = content;
    }

    public Long getId() {
        return this.id;
    }

    public Long getFilmId() {
        return this.filmId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getContent() {
        return this.content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
