ALTER TABLE filmplus.friend ADD CONSTRAINT user_friend_unique UNIQUE (user_id, friend_id);
