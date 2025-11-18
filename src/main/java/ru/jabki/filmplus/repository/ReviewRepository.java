package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Review;

@Repository
@AllArgsConstructor
public class ReviewRepository {

    private static final String INSERT = """
            INSERT INTO filmplus.review (film_id, user_id, content)
            VALUES (:film_id, :user_id, :content)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE filmplus.review
            SET film_id = :film_id, user_id = :user_id, content = :content
            WHERE id = :id
            RETURNING *;
            """;

    private static final String DELETE = """
            DELETE FROM filmplus.review
            WHERE id = :id
            """;

    private static final String GET_BY_ID = """
            SELECT *
            FROM filmplus.review
            WHERE id = :id
            """;

    private final ReviewMapper reviewMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Review insert(final Review review) {
        return jdbcTemplate.queryForObject(INSERT, reviewToSql(review), reviewMapper);
    }

    public Review update(final Review review) {
        return jdbcTemplate.queryForObject(UPDATE, reviewToSql(review), reviewMapper);
    }

    public void delete(final Long id) {
        jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
    }

    public Review getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), reviewMapper);
        } catch (DataAccessException e) {
            throw new BadRequestException(String.format("Отзыв с id %s не найден", id));
        }
    }

    public MapSqlParameterSource reviewToSql(final Review review) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", review.getId());
        params.addValue("film_id", review.getFilmId());
        params.addValue("user_id", review.getUserId());
        params.addValue("content", review.getContent());

        return params;
    }
}
