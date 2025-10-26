package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.ReviewException;
import ru.jabki.filmplus.model.Review;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReviewService {

    private static final Set<Review> reviews = new HashSet<>();

    public Review create(final Review review) {
        validate(review);
        review.setId((long)(reviews.size() + 1));
        reviews.add(review);
        return review;
    }

    public Review getById(final long id) {
        final Review review = reviews.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        if (review == null) {
            throw new ReviewException("Отзыв не найден");
        }
        return review;
    }

    public Review update(final Review review) {
        validate(review);
        final Review existReview = getById(review.getId());
        existReview.setContent(review.getContent());
        return existReview;
    }

    public void delete(final Long id) {
        reviews.remove(getById(id));
    }

    private void validate(final Review review) {
        if (review == null) {
            throw new ReviewException("Отзыв не задан");
        }
        if (!StringUtils.hasText(review.getContent())) {
            throw new ReviewException("Содержимое отзыва не задано");
        }
        (new FilmService()).getById(review.getFilmId());
        (new UserService()).getById(review.getUserId());
    }
}
