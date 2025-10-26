package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import ru.jabki.filmplus.exception.FriendException;
import ru.jabki.filmplus.exception.UserNotFoundException;
import ru.jabki.filmplus.model.Friend;

import java.util.HashSet;
import java.util.Set;

@Service
public class FriendService {

    private static final Set<Friend> friends = new HashSet<>();

    public Friend create(final Friend friend) {
        Long userId = (new UserService()).getById(friend.getUserId()).getId();

        Long friendId;
        try {
            friendId = (new UserService()).getById(friend.getFriendId()).getId();
        } catch (UserNotFoundException ex) {
            throw new FriendException("Друг не найден");
        }

        if (userId == friendId) {
            throw new FriendException("Пользователь сам себе друг (за очень редким исключением)");
        }
        Friend existFriend = getByUserIdAndFriendId(userId, friendId);
        if (existFriend != null) {
            return existFriend;
        }
        friend.setId((long)(friends.size() + 1));
        friends.add(friend);
        return friend;
    }

    public void delete(final Long id) {
        friends.remove(getById(id));
    }

    public Friend getById(final long id) {
        final Friend friend = friends.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        if (friend == null) {
            throw new FriendException("Друг не найден");
        }
        return friend;
    }

    public Friend getByUserIdAndFriendId(final long userId, final long friendId) {
        return friends.stream()
                .filter(f -> (f.getUserId() == userId) && (f.getFriendId() == friendId))
                .findFirst()
                .orElse(null);
    }
}
