package expression;

public class Lab5 implements Expression {
    @Override
    public double evaluate(double x, double c, double t) throws InvalidInputException {
        var lab1 = new Lab1();
        return lab1.evaluate(x, c, t);
    }

    @Override
    public String[] evaluateRange(double c, double t) throws InvalidInputException {
        var lab1 = new Lab4();
        return lab1.evaluateRange(c, t);
    }
}
