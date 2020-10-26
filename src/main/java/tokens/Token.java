package tokens;

public abstract class Token {
    private final int position;
    private final int length;

    public Token(int position, int length) {
        this.position = position;
        this.length = length;
    }

    public abstract String getRepresentation();

    public int getPosition() {
        return position;
    }

    public int getLength() {
        return length;
    }
}
