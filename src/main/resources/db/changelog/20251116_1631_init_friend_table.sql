CREATE TABLE filmplus.friend (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES filmplus.user (id),
    friend_id INTEGER REFERENCES filmplus.user (id),
	CONSTRAINT friend_pk UNIQUE (user_id, friend_id)
);
