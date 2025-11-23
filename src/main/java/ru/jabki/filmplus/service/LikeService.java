package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.jabki.filmplus.model.Like;
import ru.jabki.filmplus.repository.LikeRepository;

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional(rollbackFor = Exception.class)
    public Like create(final Like like) {
        return likeRepository.insert(like);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(final Long id) {
        likeRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Like getById(final long id) {
        return likeRepository.getById(id);
    }
}
