import exceptions.PositionalException;
import tokens.*;
import tokens.Number;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class Parser {

    /*
     * Shunting-yard algorithm
     * converts infix expression into postfix
     */
    //todo ( must follow function name. Check if comma placed at correct position and if there is correct quantity of commas
    public Queue<Token> parse (List<Token> tokens) throws PositionalException {
        Queue<Token> outputQueue = new ArrayDeque<>(tokens.size());
        Deque<Token> stack = new ArrayDeque<>();

        for (Token current : tokens) {
            if (current instanceof Number || current instanceof Constant || current instanceof Variable) {
                outputQueue.add(current);
            }
            else if (current instanceof Function) {
                stack.addFirst(current);
            }
            else if (current instanceof Operator) {
                while (stack.size() > 0) {
                    if (stack.getFirst() instanceof Operator) {
                        Operator currOp = (Operator) current;
                        Operator.OperatorType.Associativity currOpAssoc = currOp.getAssociativity();
                        Operator prevOp = (Operator) stack.getFirst();
                        if ((currOpAssoc == Operator.OperatorType.Associativity.LEFT && currOp.getPrecedence() <= prevOp.getPrecedence()) ||
                                (currOpAssoc == Operator.OperatorType.Associativity.RIGHT && currOp.getPrecedence() < prevOp.getPrecedence())) {
                            outputQueue.add(stack.removeFirst());
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }

                stack.addFirst(current);
            }
            else if (current instanceof LeftParenthesis) {
                stack.addFirst(current);
            }
            else if (current instanceof RightParenthesis) {
                boolean leftParenthesisExists = false;
                while (stack.size() > 0) {
                    if (stack.getFirst() instanceof LeftParenthesis) {
                        leftParenthesisExists = true;
                        stack.removeFirst();
                        break;
                    }
                    else {
                        outputQueue.add(stack.removeFirst());
                    }
                }

                if (!leftParenthesisExists) {
                    throw new PositionalException("There is no corresponding parenthesis", current.getPosition());
                }

                if (stack.size() > 0 && stack.getFirst() instanceof Function) {
                    outputQueue.add(stack.removeFirst());
                }
            }
            else if (current instanceof Comma) {
                boolean leftParenthesisExists = false;
                while (stack.size() > 0) {
                    if (stack.getFirst() instanceof LeftParenthesis) {
                        leftParenthesisExists = true;
                        break;
                    }
                    else {
                        outputQueue.add(stack.removeFirst());
                    }
                }
                if (!leftParenthesisExists) {
                    //todo throw exception either the separator was misplaced or parentheses were mismatched
                }
            }
            else {
                throw new IllegalStateException("Unexpected token: " + current);
            }
        }

        while (stack.size() > 0) {
            if (stack.getFirst() instanceof LeftParenthesis) {
                throw new PositionalException("There is no corresponding parenthesis", stack.getFirst().getPosition());
            }
            else {
                outputQueue.add(stack.removeFirst());
            }
        }

        return outputQueue;
    }
}
