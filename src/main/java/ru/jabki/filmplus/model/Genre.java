package ru.jabki.filmplus.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Genre {
    ACTION(1),
    ADVENTURE(2),
    ANIMATED(3),
    COMEDY(4),
    DRAMA(4),
    FANTASY(5),
    HISTORICAL(6),
    HORROR(7),
    MELODRAMA(8),
    NOIR(9),
    PORNOGRAPHIC(10),
    ROMANCE(11),
    MUSICAL(12),
    SCIENCE(13),
    THRILLER(14),
    WESTERN(15);

    private final int id;

    Genre(int id) {
        this.id = id;
    }

    // https://stackoverflow.com/questions/27484353/gettin-enum-types-may-not-be-instantiated-exception
    final static Map<Integer, Genre> map = new HashMap<>();

    static {
        for (Genre genre : Genre.values()) {
            map.put(genre.id, genre);
        }
    }

    public static Genre getById(int id) {
        return map.get(id);
    }

}
