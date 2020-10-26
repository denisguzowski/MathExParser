package exceptions;

public class PositionalException extends Exception {
    private final int position;

    public PositionalException(String message, int position) {
        super(message);
        this.position = position;
    }

    public PositionalException(String message, Throwable cause, int position) {
        super(message, cause);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
