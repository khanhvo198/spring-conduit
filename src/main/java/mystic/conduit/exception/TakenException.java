package mystic.conduit.exception;

public class TakenException extends RuntimeException {
    public TakenException() {
        super("has already been taken");
    }
}
