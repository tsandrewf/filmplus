package ru.jabki.filmplus.model;

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

    public int gatId() {
        return this.id;
    }

    Genre(int id) {
        this.id = id;
    }
};
