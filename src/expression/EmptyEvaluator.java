package expression;

public class EmptyEvaluator implements Expression {
    @Override
    public double evaluate(double x, double c, double t) {
        return 0;
    }

    @Override
    public String[] evaluateRange(double c, double t) throws InvalidInputException {
        return new String[0];
    }
}
