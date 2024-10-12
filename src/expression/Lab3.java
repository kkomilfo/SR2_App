package expression;

public class Lab3 implements Expression {
    @Override
    public double evaluate(double x, double c, double t) throws InvalidInputException {
        try {
            validateInput(x, c, t);
            var evaluator = new Lab1();
            return evaluator.evaluate(x, c, t);
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    @Override
    public String[] evaluateRange(double c, double t) throws InvalidInputException {
        return new String[0];
    }

    private void validateInput(double x, double c, double t) throws InvalidInputException {
        if (x <= 0) {
            throw new InvalidInputException("Invalid input x cannot be less than 0.");
        } else if (c == 0) {
            throw new InvalidInputException("Invalid input c cannot be equal 0.");
        } else if (c + t == 0) {
            throw new InvalidInputException("Invalid input c + t cannot be equal 0.");
        }
    }
}
