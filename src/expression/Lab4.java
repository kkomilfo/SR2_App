package expression;

import java.util.ArrayList;

public class Lab4 implements Expression {
    @Override
    public double evaluate(double x, double c, double t) {
        return 0;
    }

    @Override
    public String[] evaluateRange(double c, double t) {
        ArrayList<String> stringList = new ArrayList<>();

        double cotC = 1.0 / Math.tan(c);
        double cotCSquared = cotC * cotC;

        double N = 2; // Мій варіант
        double startX = -10 - 2.5 * N;
        double endX = 5 + 1.2 * N;
        double deltaX = 0.5 + N / 20.0;

        for (double x = startX; x <= endX; x += deltaX) {
            if (!validateInput(x, c, t)) {
                continue;
            }
            double result = calculateExpression(x, c, t, cotCSquared);

            String formattedString = String.format("x = %.2f, результат = %.4f", x, result);
            stringList.add(formattedString);
        }
        return stringList.toArray(new String[0]);
    }

    public static double calculateExpression(double x, double c, double t, double cotCSquared) {
        double numerator = 2 * x * x + 5;
        double denominator = Math.sqrt(c + t);
        return cotCSquared * c + (numerator / denominator);
    }

    private boolean validateInput(double x, double c, double t)  {
        if (x <= 0) {
            return false;
        } else if (c == 0) {
            return false;
        } else if (c + t == 0) {
            return false;
        }
        return true;
    }
}
