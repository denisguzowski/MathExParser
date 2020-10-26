package tokens;

import java.util.function.BiFunction;

public class Function extends ApplicableToken{
    public enum FunctionType {
        SIN (1, (a, b) -> Math.sin(a)),
        COS (1, (a, b) -> Math.cos(a)),
        TAN (1, (a, b) -> Math.tan(a)),
        EXP (1, (a, b) -> Math.exp(a)),
        ABS (1, (a, b) -> Math.abs(a)),
        POW (2, Math::pow),
        ROOT (2, (a, b) ->  Math.pow(b, 1 / a));

        private final int arity;
        private final BiFunction<Double, Double, Double> function;

        FunctionType(int arity, BiFunction<Double, Double, Double> function) {
            this.arity = arity;
            this.function = function;
        }

        public int getArity() {
            return arity;
        }

        public double apply(double... args) {
            if (args.length != arity) throw new IllegalArgumentException("");

            if(args.length == 1) return function.apply(args[0], null); //second operand means nothing

            return function.apply(args[0], args[1]);
        }

        public String getRepresentation() {
            return this.name().toLowerCase();
        }
    }

    private final FunctionType functionType;

    public Function(int position, int length, FunctionType functionType) {
        super(position, length);
        this.functionType = functionType;
    }

    @Override
    public double apply(double... args) {
        return functionType.apply(args);
    }

    @Override
    public int getArity() {
        return functionType.getArity();
    }

    @Override
    public String getRepresentation() {
        return functionType.getRepresentation();
    }
}
