package ru.jabki.filmplus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.FriendException;
import ru.jabki.filmplus.exception.LikeException;
import ru.jabki.filmplus.exception.ReviewException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.exception.UserNotFoundException;
import ru.jabki.filmplus.model.ApiError;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiError> handleUserError(final UserException userException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                userException.getMessage()
                        )
                );
    }

    @ExceptionHandler(FilmException.class)
    public ResponseEntity<ApiError> handleFilmError(final FilmException filmException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                filmException.getMessage()
                        )
                );
    }

    @ExceptionHandler(LikeException.class)
    public ResponseEntity<ApiError> handleFilmError(final LikeException likeException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                likeException.getMessage()
                        )
                );
    }

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<ApiError> handleFilmError(final ReviewException reviewException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                reviewException.getMessage()
                        )
                );
    }

    @ExceptionHandler(FriendException.class)
    public ResponseEntity<ApiError> handleFilmError(final FriendException friendException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                friendException.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserError(final UserNotFoundException userNotFoundException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                userNotFoundException.getMessage()
                        )
                );
    }
}
