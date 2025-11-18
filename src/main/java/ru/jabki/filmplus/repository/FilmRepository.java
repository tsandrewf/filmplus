package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class FilmRepository {

    private static final String INSERT = """
            INSERT INTO filmplus.film (name, description, releaseDate, duration)
            VALUES (:name, :description, :releaseDate, :duration)
            RETURNING *
            """;

    private static final String UPDATE = """
            UPDATE filmplus.film
            SET name = :name, description = :description, releaseDate = :releaseDate, duration = :duration
            WHERE id = :id
            RETURNING *
            """;

    private static final String DELETE = """
            DELETE FROM filmplus.film
            WHERE id = :id
            """;

    private static final String GET_BY_ID = """
            SELECT *
                 , (SELECT array_agg(fg.genge)
                    FROM filmplus.film_genre fg
                    WHERE fg.film_id = f.id
                   ) as genres
            FROM filmplus.film f
            WHERE id = :id
            """;

    private static String getFilmGenreSetSql(Film film, boolean isUpdate) {
        String filmGenreSetSql;

        if (isUpdate) {
            filmGenreSetSql = """
            DELETE FROM filmplus.film_genre
            WHERE film_id = :id
            """;

            if (film.getGenres() != null && !film.getGenres().isEmpty()) {
                filmGenreSetSql = filmGenreSetSql.concat(" AND genge NOT IN (");

                boolean needComma = false;
                for (Genre genre : film.getGenres()) {
                    if (needComma) {
                        filmGenreSetSql = filmGenreSetSql.concat(", ");
                    } else {
                        needComma = true;
                    }
                    filmGenreSetSql = filmGenreSetSql.concat(Integer.toString(genre.getId()));
                }

                filmGenreSetSql = filmGenreSetSql.concat(")");
            }

            filmGenreSetSql = filmGenreSetSql.concat("; ");
        } else {
            filmGenreSetSql = "";
        }

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            filmGenreSetSql = filmGenreSetSql.concat("""
                INSERT INTO filmplus.film_genre (film_id, genge)
                VALUES
                """);

            boolean needComma = false;
            for (Genre genre : film.getGenres()) {
                if (needComma) {
                    filmGenreSetSql = filmGenreSetSql.concat(", ");
                } else {
                    needComma = true;
                }
                filmGenreSetSql = filmGenreSetSql.concat("(:id, " + genre.getId() + ")");
            }

            if (isUpdate) {
                filmGenreSetSql = filmGenreSetSql.concat(" ON CONFLICT (film_id, genge) DO NOTHING");
            }
        }

        return filmGenreSetSql;
    }

    private static String getSearchSql(final String name, final String description, final Set<Genre> genres) {
        String prefix = "WHERE";
        String searchSql = """
            SELECT *
                 , (SELECT array_agg(fg.genge)
                    FROM filmplus.film_genre fg
                    WHERE fg.film_id = f.id
                   ) as genres
            FROM filmplus.film f
                """;
        if (name != null) {
            searchSql = searchSql.concat(" " + prefix + " UPPER(name) LIKE UPPER('%' || :name || '%')");
            prefix = "AND";
        }
        if (description != null) {
            searchSql = searchSql.concat(" " + prefix + " UPPER(description) LIKE UPPER('%' || :description || '%')");
            prefix = "AND";
        }
        if (genres != null && !genres.isEmpty()) {
            searchSql = searchSql.concat(" " + prefix + " (SELECT COUNT(1) FROM filmplus.film_genre fg WHERE fg.film_id = f.id");

            searchSql = searchSql.concat(" AND fg.genge IN (");
            boolean needComma = false;
            for (Genre genre : genres) {
                if (needComma) {
                    searchSql = searchSql.concat(", ");
                } else {
                    needComma = true;
                }
                searchSql = searchSql.concat(Integer.toString(genre.getId()));
            }
            searchSql = searchSql.concat(")");

            searchSql = searchSql.concat(" " + ")  = " + genres.size());
        }
        return searchSql;
    }

    private final FilmMapper filmMapper;
    private final FilmWithGenreMapper filmWithGenreMapper;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Film insert(final Film film) {
        Film newFilm = jdbcTemplate.queryForObject(INSERT, filmToSql(film), filmMapper);

        assert newFilm != null;

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            jdbcTemplate.update(getFilmGenreSetSql(film, false), new MapSqlParameterSource("id", newFilm.getId()));
        }

        return getById(newFilm.getId());
    }

    public Film update(final Film film) {
        Film updFilm = jdbcTemplate.queryForObject(UPDATE, filmToSql(film), filmMapper);
        assert updFilm != null;
        jdbcTemplate.update(getFilmGenreSetSql(film, true), new MapSqlParameterSource("id", updFilm.getId()));
        return getById(updFilm.getId());
    }

    public void delete(final Long id) {
        try {
            jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
        } catch (Exception e) {
            throw new BadRequestException(String.format("Фильм с id %d не найден", id));
        }
    }

    public Film getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), filmWithGenreMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Фильм с id %d не найден", id));
        }
    }

    public Set<Film> search(String name, String description, Set<Genre> genres) {
        List<Film> films = jdbcTemplate.query(getSearchSql(name, description, genres), searchToSql(name, description, genres), filmWithGenreMapper);
        return new HashSet<>(films);
    }

    public MapSqlParameterSource filmToSql(final Film film) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", film.getId());
        params.addValue("name", film.getName());
        params.addValue("description", film.getDescription());
        params.addValue("releaseDate", film.getReleaseDate());
        params.addValue("duration", film.getDuration());
        params.addValue("genres", film.getGenres());

        return params;
    }

    public MapSqlParameterSource searchToSql(final String name, final String description, final Set<Genre> genres) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("name", name);
        params.addValue("description", description);
        params.addValue("genres", genres);

        return params;
    }
}
