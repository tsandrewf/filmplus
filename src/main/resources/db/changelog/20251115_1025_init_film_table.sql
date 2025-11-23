CREATE TABLE filmplus.film (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    releaseDate DATE NOT NULL,
    duration INTEGER NOT NULL
);
