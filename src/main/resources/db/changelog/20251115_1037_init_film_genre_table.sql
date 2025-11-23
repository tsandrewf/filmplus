CREATE TABLE filmplus.film_genre (
    film_id INTEGER REFERENCES filmplus.film (id),
    genge INTEGER CHECK (genge BETWEEN 1 AND 15),
	CONSTRAINT film_genre_pk PRIMARY KEY (film_id, genge)
);
