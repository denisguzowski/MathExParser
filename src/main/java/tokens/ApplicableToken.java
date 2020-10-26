package tokens;

public abstract class ApplicableToken extends Token{

    public ApplicableToken(int position, int length) {
        super(position, length);
    }

    public abstract int getArity();

    public abstract double apply(double... args);
}
