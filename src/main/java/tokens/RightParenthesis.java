package tokens;

public class RightParenthesis extends Token {
    public RightParenthesis(int position, int length) {
        super(position, length);
    }

    @Override
    public String getRepresentation() {
        return String.valueOf(')');
    }
}
