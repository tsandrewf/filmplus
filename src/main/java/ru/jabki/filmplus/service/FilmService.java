package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;
import ru.jabki.filmplus.repository.FilmRepository;

import java.time.LocalDate;
import java.util.Set;

@Service
@AllArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    @Transactional(rollbackFor = Exception.class)
    public Film create(final Film film) {
        validate(film);
        return filmRepository.insert(film);
    }

    @Transactional(readOnly = true)
    public Film getById(Long id) {
        final Film film = filmRepository.getById(id);
        if (film == null) {
            throw new FilmException("Фильм не найден");
        }
        return film;
    }

    @Transactional(rollbackFor = Exception.class)
    public Film update(final Film film) {
        validate(film);
        final Film existFilm = getById(film.getId());
        existFilm.setName(film.getName());
        existFilm.setDescription(film.getDescription());
        existFilm.setReleaseDate(film.getReleaseDate());
        existFilm.setDuration(film.getDuration());
        existFilm.setGenres(film.getGenres());
        filmRepository.update(existFilm);
        return existFilm;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(final Long id) {
        filmRepository.delete(id);
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
        return filmRepository.search(name, description, genres);
    }
}
