package tokens;

public class Constant extends NumericalToken{
    public enum ConstantType {
        E (Math.E, String.valueOf('e')),
        PI (Math.PI, String.valueOf('π'));

        private final double value;
        private final String representation;

        ConstantType(double value, String representation) {
            this.value = value;
            this.representation = representation;
        }

        public double getValue() {
            return value;
        }

        public String getRepresentation() {
            return representation;
        }
    }

    private final ConstantType constantType;

    public Constant(int position, int length, ConstantType constantType) {
        super(position, length);
        this.constantType = constantType;
    }

    @Override
    public double getValue() {
        return constantType.getValue();
    }

    @Override
    public String getRepresentation() {
        return constantType.getRepresentation();
    }
}
