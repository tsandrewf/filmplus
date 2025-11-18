CREATE TABLE filmplus.review (
    id SERIAL PRIMARY KEY,
    film_id INTEGER REFERENCES filmplus.film (id),
    user_id INTEGER REFERENCES filmplus.user (id),
    content VARCHAR NOT NULL
);
