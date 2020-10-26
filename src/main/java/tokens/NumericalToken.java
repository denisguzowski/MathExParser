package tokens;

public abstract class NumericalToken extends Token{

    public NumericalToken(int position, int length) {
        super(position, length);
    }

    public abstract double getValue();
}
