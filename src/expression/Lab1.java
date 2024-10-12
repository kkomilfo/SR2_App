package expression;

public class Lab1 implements Expression {
    @Override
    public double evaluate(double x, double c, double t) {
        double cotC = 1.0 / Math.tan(c);
        double cotCSquared = cotC * cotC;
        double numerator = 2 * x * x + 5;
        double denominator = Math.sqrt(c + t);
        return cotCSquared * c + (numerator / denominator);
    }

    @Override
    public String[] evaluateRange(double c, double t) throws InvalidInputException {
        return new String[0];
    }
}
