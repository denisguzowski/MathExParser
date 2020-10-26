package tokens;

public class Variable extends NumericalToken {
    private final String representation;
    private double value;
    private boolean isReplaced = false;

    public Variable(int position, int length, String representation) {
        super(position, length);
        this.representation = representation;
    }

    public void setValue(double value) {
        this.value = value;
        isReplaced = true;
    }

    @Override
    public double getValue() {
        if (isReplaced) return value;
        else throw new IllegalStateException("The variable is not replaced");
    }

    @Override
    public String getRepresentation() {
        return representation;
    }
}

