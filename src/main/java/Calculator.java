import exceptions.NotReplacedVariableException;
import exceptions.PositionalException;
import tokens.*;
import java.util.*;

public class Calculator {

    public double calculate (Queue<Token> postfixExpression, Double valueOfTheVariable) throws PositionalException {
        Deque<Double> stack = new ArrayDeque<>();
        String prevVarRepresentation = null;

        for (Token token : postfixExpression) {
            if (token instanceof NumericalToken) {
                if (token instanceof Variable) {
                    if (valueOfTheVariable == null) {
                        throw new NotReplacedVariableException("The variable cannot be replaced", (Variable) token);
                    }
                    else {
                        if (prevVarRepresentation == null || prevVarRepresentation.equalsIgnoreCase(token.getRepresentation())) {
                            ((Variable) token).setValue(valueOfTheVariable);
                            prevVarRepresentation = token.getRepresentation();
                        }
                        else {
                            throw new PositionalException("Second variable. Expressions with more than one variable are not supported", token.getPosition());
                        }
                    }
                }

                stack.addFirst(((NumericalToken) token).getValue());
            }
            else if (token instanceof ApplicableToken) {
                ApplicableToken applicableToken = (ApplicableToken) token;
                double[] args = new double[applicableToken.getArity()];
                for (int i = applicableToken.getArity() - 1, actualNumberOfArgs = 0; i >= 0; i--) {
                    try {
                        args[i] = stack.removeFirst();
                        actualNumberOfArgs++;
                    }
                    catch (NoSuchElementException e) {
                        throw new PositionalException(String.format("Wrong number of arguments or operands. Expected %d, but found %d.", applicableToken.getArity(), actualNumberOfArgs), applicableToken.getPosition());
                    }
                }
                //todo catch possible exceptions
                stack.addFirst(applicableToken.apply(args));
            }
            else {
                throw new IllegalStateException("Unexpected token: " + token);
            }
        }

        if (stack.size() == 1) {
            return stack.removeFirst();
        }
        else {
            //todo throw better exception
            throw new IllegalStateException("Stack should be of size 1, but size = " + stack.size());
        }
    }

    public List<Point> preparePoints (int leftEndpoint, int rightEndpoint, int numberOfSteps, Queue<Token> postfixNotation) throws PositionalException {
        List<Point> points = new ArrayList<>();

        Calculator calculator = new Calculator();
        double x = leftEndpoint;
        while (x <= rightEndpoint) {
            double y = calculator.calculate(postfixNotation, x);
            points.add(new Point(x, y));
            x += (rightEndpoint - leftEndpoint) / (double) numberOfSteps;
        }

        return points;
    }
}
