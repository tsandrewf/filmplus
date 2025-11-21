SELECT * FROM filmplus.film_genre;
ALTER TABLE filmplus.film_genre
   ADD CONSTRAINT film_genre_genre_check CHECK (genre BETWEEN 1 AND 15);
