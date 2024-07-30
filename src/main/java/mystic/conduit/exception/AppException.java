package mystic.conduit.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    public AppException(Error error) {
        super(error.getMessage());
    }
}
