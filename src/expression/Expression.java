package expression;

import java.lang.reflect.Array;

public interface Expression {
    public double evaluate(double x, double c, double t) throws InvalidInputException;
    public String[] evaluateRange(double c, double t) throws InvalidInputException;
}
