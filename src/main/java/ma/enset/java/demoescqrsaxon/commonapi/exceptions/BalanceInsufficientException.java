package ma.enset.java.demoescqrsaxon.commonapi.exceptions;

public class BallanceInsufficientException extends RuntimeException {
    public BallanceInsufficientException(String message) {
        super(message);
    }
}