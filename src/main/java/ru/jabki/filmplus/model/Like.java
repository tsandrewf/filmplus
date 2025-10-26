package ru.jabki.filmplus.model;

public class Like {

    private Long id;
    private Long filmId;
    private Long userId;

    public Like(Long id, Long filmId, Long userId) {
        this.id = id;
        this.filmId = filmId;
        this.userId = userId;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
