package mystic.conduit.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<?> handleEmailTakenException(EmailTakenException ex, WebRequest req) {
        return handleTakenException("email", ex, req);
    }


    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<?> handleUsernameTaken(UsernameTakenException ex, WebRequest req) {
        return handleTakenException("username", ex, req);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex, WebRequest req) {
        return handleNotFoundException("user", ex, req);
    }

    @ExceptionHandler(SlugTakenException.class)
    public ResponseEntity<?> handleSlugTaken(SlugTakenException ex, WebRequest req) {
        return handleTakenException("slug", ex, req);
    }

    private ResponseEntity<?> handleNotFoundException(String context, NotFoundException ex, WebRequest req) {
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.NOT_FOUND;

        Map<String, Object> error = toMap("errors", toMap(context, toList(message)));
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, req);

    }

    private ResponseEntity<?> handleTakenException(String field, TakenException ex, WebRequest req) {
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        Map<String, Object> error = toMap("errors", toMap(field, toList(message)));
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, req);
    }


    private Map<String, Object> toMap(String key, Object value) {
        return new HashMap<String, Object>() {
            {
                put(key, value);
            }
        };
    }

    private List<String> toList(String message) {
        return new ArrayList<String>() {
            {
                add(message);
            }
        };
    }

}



