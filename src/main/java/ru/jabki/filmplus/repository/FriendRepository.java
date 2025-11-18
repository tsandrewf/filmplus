package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Friend;

@Repository
@AllArgsConstructor
public class FriendRepository {

    private static final String INSERT = """
            INSERT INTO filmplus.friend (user_id, friend_id)
            VALUES (:user_id, :friend_id)
            RETURNING *
            """;

    private static final String DELETE = """
            DELETE FROM filmplus.friend
            WHERE id = :id
            """;

    private static final String GET_BY_ID = """
            SELECT *
            FROM filmplus.friend
            WHERE id = :id
            """;

    private final FriendMapper friendMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Friend insert(final Friend friend) {
        return jdbcTemplate.queryForObject(INSERT, friendToSql(friend), friendMapper);
    }

    public void delete(final Long id) {
        jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
    }

    public Friend getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), friendMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Друг по id %d не найден", id));
        }
    }

    public MapSqlParameterSource friendToSql(final Friend friend) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", friend.getId());
        params.addValue("user_id", friend.getUserId());
        params.addValue("friend_id", friend.getFriendId());

        return params;
    }
}
