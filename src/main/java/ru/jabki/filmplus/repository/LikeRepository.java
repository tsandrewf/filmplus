package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Like;

@Repository
@AllArgsConstructor
public class LikeRepository {

    private static final String INSERT = """
            INSERT INTO filmplus.like (film_id, user_id)
            VALUES (:film_id, :user_id)
            RETURNING *
            """;

    private static final String DELETE = """
            DELETE FROM filmplus.like
            WHERE id = :id
            """;

    private static final String GET_BY_ID = """
            SELECT *
            FROM filmplus.like
            WHERE id = :id
            """;

    private final LikeMapper likeMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Like insert(final Like like) {
        return jdbcTemplate.queryForObject(INSERT, likeToSql(like), likeMapper);
    }

    public void delete(final Long id) {
        jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
    }

    public Like getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), likeMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Лайк по id %d не найден", id));
        }
    }

    public MapSqlParameterSource likeToSql(final Like like) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", like.getId());
        params.addValue("film_id", like.getFilmId());
        params.addValue("user_id", like.getUserId());

        return params;
    }
}
