package exceptions;

import tokens.Variable;

public class NotReplacedVariableException extends RuntimeException {
    private final Variable variable;

    public NotReplacedVariableException(String message, Variable variable) {
        super(message);
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }
}
