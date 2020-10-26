import exceptions.PositionalException;
import tokens.*;
import tokens.Number;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Tokenizer {
    public enum CharType {
        ALPHABETIC,
        NUMERIC,
        OPERATOR,
        LEFT_PARENTHESIS,
        RIGHT_PARENTHESIS,
        COMMA,
        START
    }

    public List<Token> tokenize (String ex) throws PositionalException {
        List<Token> tokens = new ArrayList<>(ex.length());

        CharType prevType = CharType.START;
        for (int i = 0; i < ex.length(); i++) {
            char currentChar = ex.charAt(i);

            if (isAlphabetic(currentChar)) {
                int lastOfTheSameType = lastIndexOfTheSameType(ex, i, this::isAlphabetic);
                String sequence = ex.substring(i, lastOfTheSameType + 1);

                if (sequence.length() == 1) {
                    if (Arrays.stream(Constant.ConstantType.values()).map(Enum::toString).anyMatch(p -> p.equals(sequence.toUpperCase()))) {
                        tokens.add(new Constant(i, sequence.length(), Constant.ConstantType.valueOf(sequence.toUpperCase())));
                    }
                    else {
                        tokens.add(new Variable(i, sequence.length(), sequence));
                    }
                }
                else {
                    if (Arrays.stream(Function.FunctionType.values()).map(Enum::toString).anyMatch(p -> p.equals(sequence.toUpperCase()))) {
                        tokens.add(new Function(i, sequence.length(), Function.FunctionType.valueOf(sequence.toUpperCase())));
                    }
                    else if (Arrays.stream(Constant.ConstantType.values()).map(Enum::toString).anyMatch(p -> p.equals(sequence.toUpperCase()))) {
                        tokens.add(new Constant(i, sequence.length(), Constant.ConstantType.valueOf(sequence.toUpperCase())));
                    }
                    else {
                        throw new PositionalException("Unsupported function or constant", i);
                    }
                }

                i = lastOfTheSameType;
                prevType = CharType.ALPHABETIC;
            }
            else if (isNumeric(currentChar)) {
                int lastOfTheSameType = lastIndexOfTheSameType(ex, i, this::isNumeric);

                try {
                    tokens.add(new Number(i, lastOfTheSameType + 1 - i, Double.parseDouble(ex.substring(i, lastOfTheSameType + 1))));
                }
                catch (NumberFormatException e) {
                    throw new PositionalException("Wrong number format", i);
                }

                i = lastOfTheSameType;
                prevType = CharType.NUMERIC;
            }
            else if (isOperator(currentChar)) {
                Operator.OperatorType operatorType = switch (ex.substring(i, i + 1)) {
                    case "+" -> canUnaryOperatorFollow(prevType) ? Operator.OperatorType.UNARY_PLUS : Operator.OperatorType.ADDITION;
                    case "-" -> canUnaryOperatorFollow(prevType) ? Operator.OperatorType.UNARY_MINUS : Operator.OperatorType.SUBTRACTION;
                    case "*" -> Operator.OperatorType.MULTIPLICATION;
                    case "/" -> Operator.OperatorType.DIVISION;
                    case "^" -> Operator.OperatorType.EXPONENTIATION;
                    default -> throw new IllegalStateException("Unexpected value: " + ex.charAt(i));
                };
                tokens.add(new Operator(i, 1, operatorType));
                prevType = CharType.OPERATOR;
            }
            else if (currentChar == '(') {
                tokens.add(new LeftParenthesis(i, 1));
                prevType = CharType.LEFT_PARENTHESIS;
            }
            else if (currentChar == ')') {
                tokens.add(new RightParenthesis(i, 1));
                prevType = CharType.RIGHT_PARENTHESIS;
            }
            else if (currentChar == ',') {
                tokens.add(new Comma(i, 1));
                prevType = CharType.COMMA;
            }
            else {
                if (currentChar != ' ') {
                    throw new PositionalException("Unsupported character", i);
                }
            }
        }

        return tokens;
    }

    private int lastIndexOfTheSameType (String ex, int first, Predicate<Character> typeChecker) {
        for (int i = first + 1; i < ex.length(); i++) {
            if (!typeChecker.test(ex.charAt(i))) {
                return i - 1;
            }
        }
        return ex.length() - 1;
    }

    private boolean isAlphabetic (char ch) {
        return String.valueOf(ch).matches("[A-Za-z]");
    }

    private boolean isNumeric (char ch) {
        return String.valueOf(ch).matches("[0-9.]");
    }

    private boolean isOperator (char ch) {
        return  ch == '+' ||
                ch == '-' ||
                ch == '*' ||
                ch == '/' ||
                ch == '^';
    }

    private boolean canUnaryOperatorFollow (CharType type) {
        return  type == CharType.START ||
                type == CharType.LEFT_PARENTHESIS ||
                type == CharType.COMMA ||
                type == CharType.OPERATOR;
    }
}
