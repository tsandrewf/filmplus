package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.User;

@Repository
@AllArgsConstructor
public class UserRepository {

    private static final String INSERT = """
            INSERT INTO filmplus.user (name, email, login, birthday)
            VALUES (:name, :email, :login, :birthday)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE filmplus.user
            SET name = :name, email = :email, login = :login, birthday = :birthday
            WHERE id = :id
            RETURNING *;
            """;

    private static final String DELETE = """
            DELETE FROM filmplus.user
            WHERE id = :id
            """;

    private static final String GET_BY_ID = """
            SELECT *
            FROM filmplus.user
            WHERE id = :id
            """;

    private final UserMapper userMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public User insert(final User user) {
        return jdbcTemplate.queryForObject(INSERT, userToSql(user), userMapper);
    }

    public User update(final User user) {
        return jdbcTemplate.queryForObject(UPDATE, userToSql(user), userMapper);
    }

    public void delete(final Long id) {
        jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
    }

    public User getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), userMapper);
        } catch (DataAccessException e) {
            throw new BadRequestException(String.format("Пользователь с id %s не найден", id));
        }
    }

    public MapSqlParameterSource userToSql(final User user) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", user.getId());
        params.addValue("name", user.getName());
        params.addValue("email", user.getEmail());
        params.addValue("login", user.getLogin());
        params.addValue("birthday", user.getBirthday());

        return params;
    }
}
