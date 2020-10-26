package helpers;

import tokens.Token;
import java.util.List;

public class Helper {
    public static String getExpressionRepresentation (List<Token> tokens) {
        StringBuilder representation = new StringBuilder();
        for (Token token : tokens) {
            representation.append(token.getRepresentation());
        }
        return representation.toString();
    }
}
