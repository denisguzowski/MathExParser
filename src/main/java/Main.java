import exceptions.IncorrectInputException;
import exceptions.NotReplacedVariableException;
import helpers.Helper;
import tokens.Token;
import exceptions.PositionalException;
import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser();
        Calculator calculator = new Calculator();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("f = ");
            String ex = scanner.nextLine();

            List<Token> tokens = tokenizer.tokenize(ex);
            Queue<Token> postfixNotation = parser.parse(tokens);

            double result = Double.NaN;
            String variableRepresentation = null;
            try {
                result = calculator.calculate(postfixNotation, null);
                System.out.println(result);
            }
            catch (NotReplacedVariableException e) {
                variableRepresentation = e.getVariable().getRepresentation().toLowerCase();
                System.out.println("Variable was found");
            }

            System.out.println("Left endpoint of the interval = ");
            int leftEndpoint = scanner.nextInt();
            System.out.println("Right endpoint of the interval = ");
            int rightEndpoint = scanner.nextInt();
            if (rightEndpoint <= leftEndpoint) throw new IncorrectInputException("The right endpoint must be greater than the Left");

            List<Point> points = new ArrayList<>(2);
            if (Double.isNaN(result)) {
                System.out.println("Number of steps = ");
                int numberOfSteps = scanner.nextInt();
                if (numberOfSteps <= 0) throw new IncorrectInputException("Number of steps must be greater than zero");
                points = calculator.preparePoints(leftEndpoint, rightEndpoint, numberOfSteps, postfixNotation);
            }
            else {
                points.add(new Point(leftEndpoint, result));
                points.add(new Point(rightEndpoint, result));
            }

            List<Point> finalPoints = points;
            String finalVariableRepresentation = variableRepresentation;
            SwingUtilities.invokeLater(() -> {
                String f = (finalVariableRepresentation == null) ? "f" : String.format("f(%s)", finalVariableRepresentation);
                Chart chart = new Chart(
                        "MathExParser",
                        null,
                        finalVariableRepresentation,
                        f,
                        f + "=" + Helper.getExpressionRepresentation(tokens),
                        finalPoints);

                chart.setSize(800, 400);
                chart.setLocationRelativeTo(null);
                chart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                chart.setVisible(true);
            });
        }
        catch (PositionalException e) {
            System.out.println(" ".repeat(e.getPosition()) + '↑');
            System.out.println(e.getMessage());
        }
        catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
        }
    }
}
