package tokens;

import java.util.function.DoubleBinaryOperator;

public class Operator extends ApplicableToken{
    public enum OperatorType {
        ADDITION (1, Operator.OperatorType.Associativity.LEFT, 2, Double::sum, String.valueOf('+')),
        SUBTRACTION (1, Operator.OperatorType.Associativity.LEFT, 2, (a, b) -> a - b, String.valueOf('-')),
        MULTIPLICATION (2, Operator.OperatorType.Associativity.LEFT, 2, (a, b) -> a * b, String.valueOf('*')),
        DIVISION (2, Operator.OperatorType.Associativity.LEFT, 2, (a, b) -> a / b, String.valueOf('/')),
        UNARY_PLUS (3, Operator.OperatorType.Associativity.RIGHT, 1, (a, b) -> a, String.valueOf('+')),
        UNARY_MINUS (3, Operator.OperatorType.Associativity.RIGHT, 1, (a, b) -> -a, String.valueOf('-')),
        EXPONENTIATION (4, Operator.OperatorType.Associativity.RIGHT, 2, Math::pow, String.valueOf('^'));

        public enum Associativity {
            LEFT,
            RIGHT
        }

        private final int precedence;
        private final Operator.OperatorType.Associativity associativity;
        private final int arity;
        private final DoubleBinaryOperator operator;
        private final String representation;

        OperatorType(int precedence, Operator.OperatorType.Associativity associativity, int arity, DoubleBinaryOperator operator, String representation) {
            this.precedence = precedence;
            this.associativity = associativity;
            this.arity = arity;
            this.operator = operator;
            this.representation = representation;
        }

        public int getPrecedence() {
            return precedence;
        }

        public Operator.OperatorType.Associativity getAssociativity() {
            return associativity;
        }

        public int getArity() {
            return arity;
        }

        public double apply(double... args) {
            if (args.length != arity) throw new IllegalArgumentException("");

            if(args.length == 1) return operator.applyAsDouble(args[0], 0); //second operand means nothing

            return operator.applyAsDouble(args[0], args[1]);
        }

        public String getRepresentation() {
            return representation;
        }
    }

    private final OperatorType operatorType;

    public Operator(int position, int length, OperatorType operatorType) {
        super(position, length);
        this.operatorType = operatorType;
    }

    public int getPrecedence () {
        return operatorType.getPrecedence();
    }

    public Operator.OperatorType.Associativity getAssociativity () {
        return operatorType.getAssociativity();
    }

    @Override
    public int getArity() {
        return operatorType.getArity();
    }

    @Override
    public double apply(double... args) {
        return operatorType.apply(args);
    }

    @Override
    public String getRepresentation() {
        return operatorType.getRepresentation();
    }
}
