CREATE TABLE filmplus.friend (
    user_id INTEGER REFERENCES filmplus.user (id),
    friend_id INTEGER REFERENCES filmplus.user (id),
	CONSTRAINT friend_pk PRIMARY KEY (user_id, friend_id)
);
