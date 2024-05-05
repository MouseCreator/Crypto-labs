package mouse.univ.exception;

public class BitException extends RuntimeException {
    public BitException() {
    }

    public BitException(String message) {
        super(message);
    }

    public BitException(String message, Throwable cause) {
        super(message, cause);
    }

    public BitException(Throwable cause) {
        super(cause);
    }
}
