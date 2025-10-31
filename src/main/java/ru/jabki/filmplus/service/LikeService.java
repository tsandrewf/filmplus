package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

import ru.jabki.filmplus.exception.LikeException;
import ru.jabki.filmplus.model.Like;

@Service
public class LikeService {

    private static final Set<Like> likes = new HashSet<>();
    FilmService filmService = new FilmService();
    UserService userService = new UserService();

    public Like create(final Like like) {
        Like existLike = getByFilmIdAndUserId(filmService.getById(like.getFilmId()).getId(), userService.getById(like.getUserId()).getId());
        if (existLike != null) {
            return existLike;
        }
        like.setId((long)(likes.size() + 1));
        likes.add(like);
        return like;
    }

    public void delete(final Long id) {
        likes.remove(getById(id));
    }

    public Like getById(final long id) {
        return likes.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new LikeException("Лайк не найден"));
    }

    public Like getByFilmIdAndUserId(final long filmId, final long userId) {
        return likes.stream()
                .filter(l -> (l.getFilmId() == filmId) && (l.getUserId() == userId))
                .findFirst()
                .orElse(null);
    }
}
