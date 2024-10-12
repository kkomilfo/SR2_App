package expression;

public class Lab2 implements Expression {
    @Override
    public double evaluate(double x, double c, double t) {
        if (x <= 0) {
            System.out.println("Invalid input x cannot be less than 0.");
            return 0;
        } else if (c == 0) {
            System.out.println("Invalid input c cannot be equal 0.");
            return 0;
        } else if (c + t == 0) {
            System.out.println("Invalid input c + t cannot be equal 0.");
            return 0;
        } else {
            var evaluation = new Lab1();
            return evaluation.evaluate(x, c, t);
        }
    }

    @Override
    public String[] evaluateRange(double c, double t) throws InvalidInputException {
        return new String[0];
    }
}
