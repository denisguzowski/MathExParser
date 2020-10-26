package tokens;

public class Number extends NumericalToken{
    private final double value;

    public Number(int position, int length, double value) {
        super(position, length);
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String getRepresentation() {
        return String.valueOf(value);
    }
}
