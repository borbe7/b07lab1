    // Main method for testing the class functionality
    public static void main(String[] args) throws IOException {
        // Create a zero polynomial
        Polynomial p = new Polynomial();
        System.out.println("Zero polynomial: " + p);

        // Create polynomials using arrays for coefficients and exponents
        double[] c1 = { 6, -2, 5 };
        int[] e1 = { 0, 1, 3 };
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = { 5, 3 };
        int[] e2 = { 0, 4 };
        Polynomial p2 = new Polynomial(c2, e2);

        // Add polynomials p1 and p2
        Polynomial sum = p1.add(p2);
        System.out.println("Sum: " + sum);

        // Multiply polynomials p1 and p2
        Polynomial product = p1.multiply(p2);
        System.out.println("Product: " + product);

        // Evaluate polynomials
        System.out.println("p1 evaluated at x = 2: " + p1.evaluate(2));

        // Check for root
        System.out.println("Does p1 have a root at x = 1? " + p1.hasRoot(1));

        // Save and load from file
        sum.saveToFile("polynomial.txt");
        Polynomial loadedPolynomial = new Polynomial(new File("polynomial.txt"));
        System.out.println("Loaded polynomial: " + loadedPolynomial);
    }
