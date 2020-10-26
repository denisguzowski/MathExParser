package tokens;

public class LeftParenthesis extends Token {
    public LeftParenthesis(int position, int length) {
        super(position, length);
    }

    @Override
    public String getRepresentation() {
        return String.valueOf('(');
    }
}
