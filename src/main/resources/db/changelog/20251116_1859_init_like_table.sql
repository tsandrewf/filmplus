CREATE TABLE filmplus.like (
    id SERIAL PRIMARY KEY,
    film_id INTEGER REFERENCES filmplus.film (id),
    user_id INTEGER REFERENCES filmplus.user (id),
	CONSTRAINT friend_pk UNIQUE (user_id, film_id)
);
