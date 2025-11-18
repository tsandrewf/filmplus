package ru.jabki.filmplus.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class FilmWithGenreMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Set<Genre> genres = new HashSet<>();

        Array arrayGenre = rs.getArray("genres");
        if (arrayGenre != null) {
            for (Integer genre_id : (Integer[])(arrayGenre.getArray())) {
                genres.add(Genre.getById(genre_id));
            }
        }

        return Film.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(new java.sql.Date(rs.getDate("releaseDate").getTime()).toLocalDate())
                .duration(rs.getLong("duration"))
                .genres(genres)
                .build();
    }
}
