package engine.exception_handler;

import engine.exceptions.NoAccessException;
import engine.exceptions.UserAlreadyExistException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<String> handleIdNotFound() {
        return new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserExist() {
        return new ResponseEntity<>("User has already exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<String> handleUserNotFound() {
        return new ResponseEntity<>("No access", HttpStatus.FORBIDDEN);
    }
}
