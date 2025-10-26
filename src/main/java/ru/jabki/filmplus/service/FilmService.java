package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private static final Set<Film> films = new HashSet<>();

    public Film create(final Film film) {
        validate(film);
        film.setId((long)(films.size() + 1));
        films.add(film);
        return film;
    }

    public Film getById(Long id) {
        final Film film = films.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);
        if (film == null) {
            throw new UserException("Фильм не найден");
        }
        return film;
    }

    public Film update(final Film film) {
        validate(film);
        final Film existFilm = getById(film.getId());
        existFilm.setName(film.getName());
        existFilm.setDescription(film.getDescription());
        existFilm.setReleaseDate(film.getReleaseDate());
        existFilm.setDuration(film.getDuration());
        existFilm.setGenres(film.getGenres());
        return existFilm;
    }

    public void delete(final Long id) {
        films.remove(getById(id));
    }

    private void validate(final Film film) {
        if (film == null) {
            throw new FilmException("Фильм не задан");
        }
        if (!StringUtils.hasText(film.getName())) {
            throw new FilmException("Название фильма не задано");
        }
        if (!StringUtils.hasText(film.getDescription())) {
            throw new FilmException("Описание фильма не задано");
        }
        if (film.getReleaseDate() == null) {
            throw new FilmException("Дата выхода фильма не задана");
        }
        if (film.getReleaseDate().isAfter(LocalDate.now())) {
            throw new FilmException("Дата выхода фильма больше текущей");
        }
        if (film.getDuration() == 0) {
            throw new FilmException("Продолжительность фильма не задана");
        }
        if (film.getGenres() == null) {
            throw new FilmException("Жанры фильма не заданы");
        }
    }

    public Set<Film> search(String name, String description, Set<Genre> genres) {
        return films.stream()
                .filter(f -> (!StringUtils.hasText(name) || f.getName().toLowerCase().contains(name.toLowerCase()))
                             && (!StringUtils.hasText(description) || f.getDescription().toLowerCase().contains(description.toLowerCase()))
                             && (genres == null || f.getGenres().equals(genres))
                       )
                .collect(Collectors.toSet());
    }
}
