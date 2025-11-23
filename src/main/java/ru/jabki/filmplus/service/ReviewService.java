package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.ReviewException;
import ru.jabki.filmplus.model.Review;
import ru.jabki.filmplus.repository.ReviewRepository;

@Service
@AllArgsConstructor
public class ReviewService {

    private final FilmService filmService;
    private final ReviewRepository reviewRepository;

    @Transactional(rollbackFor = Exception.class)
    public Review create(final Review review) {
        validate(review);
        return reviewRepository.insert(review);
    }

    @Transactional(readOnly = true)
    public Review getById(final long id) {
        return reviewRepository.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Review update(final Review review) {
        validate(review);
        return reviewRepository.update(review);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(final Long id) {
        reviewRepository.delete(id);
    }

    private void validate(final Review review) {
        if (review == null) {
            throw new ReviewException("Отзыв не задан");
        }
        if (!StringUtils.hasText(review.getContent())) {
            throw new ReviewException("Содержимое отзыва не задано");
        }
        filmService.getById(review.getFilmId());
    }
}
