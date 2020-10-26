package tokens;

public class Comma extends Token{
    public Comma(int position, int length) {
        super(position, length);
    }

    @Override
    public String getRepresentation() {
        return String.valueOf(',');
    }
}
