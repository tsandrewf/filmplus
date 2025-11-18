package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jabki.filmplus.exception.FriendException;
import ru.jabki.filmplus.exception.UserNotFoundException;
import ru.jabki.filmplus.model.Friend;
import ru.jabki.filmplus.repository.FriendRepository;

import java.util.Objects;

@Service
@AllArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public Friend create(final Friend friend) {
        Long userId = userService.getById(friend.getUserId()).getId();

        Long friendId;
        try {
            friendId = userService.getById(friend.getFriendId()).getId();
        } catch (UserNotFoundException ex) {
            throw new FriendException("Друг не найден");
        }

        if (Objects.equals(userId, friendId)) {
            throw new FriendException("Пользователь обычно сам себе друг (за очень редким исключением)");
        }

        return friendRepository.insert(friend);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(final Long id) {
        friendRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Friend getById(final long id) {
        return friendRepository.getById(id);
    }
}
