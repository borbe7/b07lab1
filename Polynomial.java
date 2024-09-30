public class Polynomial {
    private double[] coefficients;

    // No-argument constructor that sets the polynomial to zero (i.e., [0])
    public Polynomial() {
        this.coefficients = new double[] { 0.0 };
    }

    // Constructor that accepts an array of doubles to initialize the polynomial
    public Polynomial(double[] coefficients) {
        // Copying the coefficients array to avoid aliasing issues
        this.coefficients = coefficients.clone();
    }

    // Method that adds the current polynomial to another polynomial
    public Polynomial add(Polynomial other) {
        // Determine the maximum length between the two polynomials
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[maxLength];

        // Add coefficients of the current polynomial
        for (int i = 0; i < this.coefficients.length; i++) {
            result[i] += this.coefficients[i];
        }

        // Add coefficients of the other polynomial
        for (int i = 0; i < other.coefficients.length; i++) {
            result[i] += other.coefficients[i];
        }

        return new Polynomial(result);
    }

    // Method that evaluates the polynomial for a given value of x
    public double evaluate(double x) {
        double result = 0.0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    // Method that checks if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        // If the polynomial evaluates to zero for the given x, then x is a root
        return evaluate(x) == 0.0;
    }

    // Optional: Override toString method to display the polynomial
    @Override
    public String toString() {
        StringBuilder polynomial = new StringBuilder();
        for (int i = coefficients.length - 1; i >= 0; i--) {
            if (coefficients[i] != 0) {
                if (polynomial.length() > 0 && coefficients[i] > 0) {
                    polynomial.append(" + ");
                }
                polynomial.append(coefficients[i]);
                if (i > 0) {
                    polynomial.append("x");
                    if (i > 1) {
                        polynomial.append("^").append(i);
                    }
                }
            }
        }
        return polynomial.length() == 0 ? "0" : polynomial.toString();
    }
}

