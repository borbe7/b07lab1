import java.io.*;
import java.util.ArrayList;

public class Polynomial {
    private double[] coefficients; // Array for non-zero coefficients
    private int[] exponents;       // Array for corresponding exponents

    // a. No-argument constructor that initializes to zero polynomial
    public Polynomial() {
        this.coefficients = new double[] { 0.0 };
        this.exponents = new int[] { 0 };
    }

    // Constructor that accepts arrays for coefficients and exponents
    public Polynomial(double[] coefficients, int[] exponents) {
        if (coefficients.length != exponents.length) {
            throw new IllegalArgumentException("Coefficients and exponents arrays must have the same length");
        }
        this.coefficients = coefficients.clone();
        this.exponents = exponents.clone();
    }

    // c. Method to add the current polynomial with another polynomial
    public Polynomial add(Polynomial other) {
        ArrayList<Double> newCoefficients = new ArrayList<>();
        ArrayList<Integer> newExponents = new ArrayList<>();

        // Traverse through both polynomials
        int i = 0, j = 0;
        while (i < this.coefficients.length || j < other.coefficients.length) {
            if (i < this.coefficients.length && (j >= other.coefficients.length || this.exponents[i] < other.exponents[j])) {
                newCoefficients.add(this.coefficients[i]);
                newExponents.add(this.exponents[i]);
                i++;
            } else if (j < other.coefficients.length && (i >= this.coefficients.length || other.exponents[j] < this.exponents[i])) {
                newCoefficients.add(other.coefficients[j]);
                newExponents.add(other.exponents[j]);
                j++;
            } else {
                // Exponents are equal, add the coefficients
                double sum = this.coefficients[i] + other.coefficients[j];
                if (sum != 0) {
                    newCoefficients.add(sum);
                    newExponents.add(this.exponents[i]);
                }
                i++;
                j++;
            }
        }

        // Convert lists back to arrays
        return new Polynomial(newCoefficients.stream().mapToDouble(Double::doubleValue).toArray(),
                              newExponents.stream().mapToInt(Integer::intValue).toArray());
    }

    // c. Method to multiply the current polynomial with another polynomial
    public Polynomial multiply(Polynomial other) {
        ArrayList<Double> newCoefficients = new ArrayList<>();
        ArrayList<Integer> newExponents = new ArrayList<>();

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                double newCoefficient = this.coefficients[i] * other.coefficients[j];
                int newExponent = this.exponents[i] + other.exponents[j];

                // Add or update the term in the result polynomial
                int index = newExponents.indexOf(newExponent);
                if (index != -1) {
                    newCoefficients.set(index, newCoefficients.get(index) + newCoefficient);
                } else {
                    newCoefficients.add(newCoefficient);
                    newExponents.add(newExponent);
                }
            }
        }

        return new Polynomial(newCoefficients.stream().mapToDouble(Double::doubleValue).toArray(),
                              newExponents.stream().mapToInt(Integer::intValue).toArray());
    }

    // b. Method that evaluates the polynomial for a given value of x
    public double evaluate(double x) {
        double result = 0.0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    // b. Method that checks if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        return evaluate(x) == 0.0;
    }

    // d. Constructor that initializes the polynomial based on a file's contents
    public Polynomial(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        reader.close();

        // Parse the polynomial string
        String[] terms = line.split("(?=[+-])"); // Split at each + or -
        ArrayList<Double> newCoefficients = new ArrayList<>();
        ArrayList<Integer> newExponents = new ArrayList<>();

        for (String term : terms) {
            term = term.replaceAll("\\s+", ""); // Remove any spaces
            if (term.contains("x")) {
                String[] parts = term.split("x\\^?");
                double coefficient = parts[0].isEmpty() || parts[0].equals("+") ? 1 : parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]);
                int exponent = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
                newCoefficients.add(coefficient);
                newExponents.add(exponent);
            } else {
                newCoefficients.add(Double.parseDouble(term));
                newExponents.add(0);
            }
        }

        this.coefficients = newCoefficients.stream().mapToDouble(Double::doubleValue).toArray();
        this.exponents = newExponents.stream().mapToInt(Integer::intValue).toArray();
    }

    // e. Method to save the polynomial to a file in textual format
    public void saveToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(this.toString());
        writer.close();
    }

    // Optional: Override toString method to display the polynomial in a user-friendly format
    @Override
    public String toString() {
        StringBuilder polynomial = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                if (polynomial.length() > 0 && coefficients[i] > 0) {
                    polynomial.append("+");
                }
                polynomial.append(coefficients[i]);
                if (exponents[i] > 0) {
                    polynomial.append("x");
                    if (exponents[i] > 1) {
                        polynomial.append("^").append(exponents[i]);
                    }
                }
            }
        }
        return polynomial.length() == 0 ? "0" : polynomial.toString();
    }
}

