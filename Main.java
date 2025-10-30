import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // 1. Adım: Her iki sınıfın Scanner'ı birleştirildi.
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Ascii.displayWelcomeMessage();
        // Scanner sc = new Scanner(System.in); // <-- Kaldırıldı, static olan
        // kullanılacak

        // 5. Adım: Bu sınıf mevcut değil, hata vermemesi için yoruma alındı.
        // Ascii.displayWelcomeMessage();

        while (true) {
            System.out.println("=== MAIN MENU ===");
            System.out.println("[A] Primary School");
            System.out.println("[B] Secondary School");
            System.out.println("[C] High School");
            System.out.println("[D] University");
            System.out.println("[E] Terminate");
            System.out.print("Choose: ");

            String input = sc.nextLine().trim().toUpperCase();
            clearScreen();

            if (input.length() == 1 && input.matches("[A-E]")) {
                char choice = input.charAt(0); // tek karakter al
                System.out.println("You chose: " + choice);
                // clearScreen(); // Hemen temizlemek yerine case içinde yapıldı

                switch (choice) {
                    case 'A':
                        clearScreen();
                        System.out.println("→ Primary Menu çalışıyor...");
                        pause();
                        clearScreen();
                        break;
                    case 'B':
                        clearScreen();
                        secondaryMenu();
                        clearScreen();
                        break;
                    case 'C':
                        clearScreen();
                        System.out.println("→ High School Menu çalışıyor...");
                        pause();
                        clearScreen();
                        break;
                    case 'D':
                        clearScreen();
                        connectFourMenu();
                        clearScreen();
                        break;
                    case 'E':
                        System.out.println("Goodbye!");
                        sc.close();
                        return;
                }
            } else {
                clearScreen();
                System.out.println("Please enter only one letter A, B, C, D or E!\n");
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // -------------------------------OPTION-A-PRIMARY-SCHOOL-------------------------------

    // -------------------------------OPTION-B-SECONDARY-SCHOOL-------------------------------

    static String userInput = "";

    public static void secondaryMenu() {
        userInput = "";
        clearConsole();
        System.out.println("Hello this is the option B");
        System.out.println("-------------------------------");
        while (true) {
            try {
                System.out.println("Operation 1) Prime Numbers");
                System.out.println("Operation 2) Evoluation of Expression");
                System.out.println("Type 'exit' to leave");
                System.out.print("Enter an operation: ");

                

                userInput = sc.nextLine();

                switch (userInput) {
                    case "1", "Operation 1", "Prime Numbers":
                        int n;
                        clearConsole();
                        System.out.println("You have selected Prime Numbers");
                        System.out.println("-------------------------------");
                        
                        while (true) {
                            try {
                                System.out.print("Enter a positive integer (>=12): ");
                                n = sc.nextInt();
                                sc.nextLine();
                                
                                if (n < 12) { 
                                    System.out.println("Error: The number must be 12 or greater.");
                                    continue;
                                }
                                break; 
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Invalid input, please enter a number.");
                                sc.nextLine();
                            }
                        }

                        System.out.println("Sieve of Eratosthenes Algorithm");
                        eratosthenes(n);
                        System.out.println("Sieve of Sundaram Algorithm");
                        sundaram(n);
                        System.out.println("Sieve of Atkin Algorithm");

                        atkin(n);
                        break;
                    case "2", "Operation 2", "Evoluation of Expression":
                        operation2_evaluateExpression();
                        break;
                    case "exit":
                        clearConsole();
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option, please try again.");
                }

            } catch (Exception e) {
                if (e instanceof java.util.InputMismatchException) {
                    System.out.println("Invalid input, please enter a number.");
                    sc.nextLine();
                } else {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Clears the terminal screen.
     * Uses 'cls' for Windows and 'clear' for Unix-based systems.
     */
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Konsol temizlenemedi: " + e.getMessage());
        }
    }

    // -----------------------OPERATION-1-----------------------

    /**
     * Operation 1.1: Finds all primes up to n using the Sieve of Eratosthenes.
     * 
     * @param n The upper bound (inclusive) to find primes up to.
     */
    public static void eratosthenes(int n) {
        boolean[] prime = new boolean[n + 1];
        int primesInRange = 0;
        for (int i = 0; i <= n; i++) {
            if (i == 0 || i == 1) {
                prime[i] = false;
                continue;
            }
            prime[i] = true;
        }

        long startTime = System.nanoTime();
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * p; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        long endTime = System.nanoTime();

        for (int i = 0; i < prime.length; i++) {
            if (prime[i]) {
                primesInRange++;
            }
        }

        System.out.print("There are " + primesInRange + " number of primes up to " + n + "\n");
        int count = 0;
        if (primesInRange > 5) {
            for (int i = 2; i <= n; i++) {
                if (prime[i] && count < 3) {
                    System.out.print(i + ", ");
                } else if (prime[i] && (count == primesInRange - 2)) {
                    System.out.print(i + ", ");
                } else if (prime[i] && (count == primesInRange - 1)) {
                    System.out.println(i + " ");
                }

                if (prime[i]) {
                    count++;
                }
            }
        } else {
            for (int i = 2; i <= n; i++) {
                if (primesInRange > 0) {
                    if (primesInRange == 1 && prime[i]) {
                        System.out.println(i);
                        primesInRange--;
                    } else if (prime[i]) {
                        System.out.print(i + ", ");
                        primesInRange--;
                    }
                }
            }
        }
        long totalTime = endTime - startTime;
        System.out.println("Time taken to compute primes up to " + n + ": " + totalTime + " nanoseconds");
        System.out.println("-------------------------------");

    }

    /**
     * Operation 1.2: Finds all primes up to n using the Sieve of Sundaram.
     * 
     * @param n The upper bound (inclusive) to find primes up to.
     */
    public static void sundaram(int n) {
        int nHalf = n / 2;
        boolean[] marked = new boolean[nHalf + 1];
        long startTime = System.nanoTime();

        for (int i = 1; i <= nHalf; i++) {
            for (int j = i;; j++) {
                long index = (long) i + (long) j + (2L * i * j);

                if (index > nHalf)
                    break;

                marked[(int) index] = true;
            }
        }

        List<Integer> primes = new ArrayList<>();
        primes.add(0, 2);
        for (int i = 1; i <= n / 2; i++) {
            if (!marked[i]) {
                if (2 * i + 1 == 2) {
                    continue;
                } else if (2 * i + 1 <= n)
                    primes.add((2 * i + 1));
            }
        }

        Long endTime = System.nanoTime();
        if (primes.size() >= 5) {
            System.out.print(primes.get(0) + ", ");
            System.out.print(primes.get(1) + ", ");
            System.out.print(primes.get(2) + ", ");
            System.out.print(primes.get(primes.size() - 2) + ", ");
            System.out.println(primes.get(primes.size() - 1));
        } else {
            for (int i = 0; i < primes.size(); i++) {
                if (i == primes.size() - 1) {
                    System.out.println(primes.get(i));
                } else {
                    System.out.print(primes.get(i) + ", ");
                }
            }
        }
        System.out.println();
        Long totalTime = endTime - startTime;
        System.out.println("Time taken to compute primes up to " + n + ": " + totalTime + " nanoseconds");
        System.out.println("-------------------------------");

    }

    /**
     * Operation 1.3: Finds all primes up to n using the Sieve of Atkin.
     * 
     * @param n The upper bound (inclusive) to find primes up to.
     */
    public static void atkin(int n) {
        boolean[] marked = new boolean[n + 1];
        marked[2] = true;
        marked[3] = true;
        Long startTime = System.nanoTime();
        int primesInRange = 0;

        for (int x = 1; x * x <= n; x++) {
            for (int y = 1; y * y <= n; y++) {

                // step 1
                long n1 = (4L * x * x) + (y * y);
                if (n1 <= n) {
                    if (n1 % 12 == 1 || n1 % 12 == 5) {
                        if (marked[(int) n1]) {
                            marked[(int) n1] = false;
                        } else {
                            marked[(int) n1] = true;
                        }
                    }
                }

                // step 2
                long n2 = (3L * x * x) + (y * y);
                if (n2 <= n) {
                    if (n2 % 12 == 7) {
                        if (marked[(int) n2]) {
                            marked[(int) n2] = false;
                        } else {
                            marked[(int) n2] = true;
                        }
                    }
                }

                // step 3
                if (x > y) {
                    long n3 = (3L * x * x) - (y * y);
                    if (n3 <= n) {
                        if (n3 % 12 == 11) {
                            if (marked[(int) n3]) {
                                marked[(int) n3] = false;
                            } else {
                                marked[(int) n3] = true;
                            }
                        }
                    }
                }
            }
        }

        for (int r = 5; (long) r * r <= n; r++) {
            if (marked[r]) {
                long rSquare = (long) r * r;

                for (long i = rSquare; i <= n; i += rSquare) {

                    marked[(int) i] = false;
                }
            }
        }

        Long endTime = System.nanoTime();

        for (int i = 0; i < marked.length; i++) {
            if (marked[i]) {
                primesInRange++;
            }
        }

        int count = 1;

        for (int i = 2; i <= n; i++) {
            if (marked[i] && count == primesInRange) {
                System.out.println(i);
            } else if (marked[i] && count <= 3) {
                System.out.print(i + ", ");
            } else if (marked[i] && count == (primesInRange - 1)) {
                System.out.print(i + ", ");
            }
            if (marked[i]) {
                count++;
            }
        }

        System.out.println();
        Long totalTime = endTime - startTime;
        System.out.println("Time taken to compute primes up to " + n + ": " + totalTime + " nanoseconds");
        System.out.println("-------------------------------");
    }

    // -----------------------OPERATION-2-----------------------

    /**
     * Main function for Operation 2.
     * Handles the user input loop, validation, and starts the recursive evaluation.
     */
    private static void operation2_evaluateExpression() {
        clearConsole();
        System.out.println("You have selected Step-by-step Evaluation of Expression");
        System.out.println("Enter an expression using digits and + - x : ( ). Type 'back' to return.");
        System.out.println("-------------------------------");
        
        while (true) {
            System.out.print("expr> ");
            String expr = sc.nextLine();
            if (expr == null) continue;
            expr = expr.trim();
            if (expr.equalsIgnoreCase("back")) {
                clearConsole();
                break;
            }

            if (!isValidExpression(expr)) {
                System.out.println("re-enter a valid expression.");
                continue;
            }

            try {
                String currentExpr = expr.replaceAll("\\s+", "").replaceAll("\\(\\+", "(");
                
                solveRecursively(currentExpr);

            } catch (ArithmeticException e) {
                System.out.println("re-enter a valid expression.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. re-enter a valid expression.");
            }
        }
    }

    /**
     * This is the RECURSIVE helper function that replaces the while loop.
     * It evaluates one step, prints it, and then calls itself with the new expression.
     * @param currentExpr The expression to be evaluated in this step.
     */
    private static void solveRecursively(String currentExpr) {

        //stop condition
        if (isNumeric(currentExpr)) {
            return;
        }


        String nextStep = evaluateOneStep(currentExpr);
        
        System.out.println("= " + prettyPrint(nextStep));

        solveRecursively(nextStep);
    }

    /**
     * Finds and performs just one operation (the next one) based on precedence.
     * Precedence Order:
     * 1. Evaluate innermost parenthesis (...)
     * 2. Unwrap simple parenthesis (number) -> number
     * 3. Simplify adjacent operators -- -> +, +- -> -, -+ -> -
     * 4. Multiplication / Division
     * 5. Addition / Subtraction
     * @param expr The current expression string.
     * @return The expression string after performing one step.
     */
    private static String evaluateOneStep(String expr) {
        String cleanedExpr = expr.replace('x', '*').replace(':', '/');

        int rParen = cleanedExpr.indexOf(')');
        if (rParen != -1) {
            int lParen = cleanedExpr.lastIndexOf('(', rParen);
            String innerExpr = cleanedExpr.substring(lParen + 1, rParen);
            String result = reduceFully(innerExpr);
            String newExpr = cleanedExpr.substring(0, lParen) + result + cleanedExpr.substring(rParen + 1);
            return tidy(newExpr);
        }
        
        Matcher m = Pattern.compile("\\((\\-?[0-9]+)\\)").matcher(cleanedExpr);
        if (m.find()) {
            return cleanedExpr.substring(0, m.start()) + m.group(1) + cleanedExpr.substring(m.end());
        }

        if (cleanedExpr.contains("--")) {
            return cleanedExpr.replaceFirst("--", "+");
        }
        if (cleanedExpr.contains("+-")) {
            return cleanedExpr.replaceFirst("\\+-", "-");
        }
        if (cleanedExpr.contains("-+")) {
            return cleanedExpr.replaceFirst("-\\+", "-");
        }

        for (int i = 0; i < cleanedExpr.length(); i++) {
            char c = cleanedExpr.charAt(i);
            if (c == '*' || c == '/') {
                return performOperation(cleanedExpr, i);
            }
        }

        for (int i = 1; i < cleanedExpr.length(); i++) { 
            char c = cleanedExpr.charAt(i);
            if (c == '+' || c == '-') {
                return performOperation(cleanedExpr, i);
            }
        }

        return cleanedExpr;
    }

    /**
     * Solves an expression (like one inside parentheses) all at once, without
     * steps.
     * Used to get a single result for a sub-expression.
     * 
     * @param expr The expression to solve (e.g., "5*2-4").
     * @return The final result as a string (e.g., "6").
     */
    private static String reduceFully(String expr) {
        String current = expr;

        for (int guard = 0; guard < 100; guard++) {
            String before = current;

            current = current.replaceAll("\\((\\-?[0-9]+)\\)", "$1");
            current = current.replaceAll("--", "+");
            current = current.replaceAll("\\+-", "-");
            current = current.replaceAll("-\\+", "-");

            boolean foundMulDiv = false;
            for (int i = 0; i < current.length(); i++) {
                char c = current.charAt(i);
                if (c == '*' || c == '/') {
                    current = performOperation(current, i);
                    foundMulDiv = true;
                    break;
                }
            }
            if (foundMulDiv)
                continue;

            boolean foundAddSub = false;
            for (int i = 1; i < current.length(); i++) {
                char c = current.charAt(i);
                if (c == '+' || c == '-') {
                    current = performOperation(current, i);
                    foundAddSub = true;
                    break;
                }
            }
            if (foundAddSub)
                continue;

            // If nothing changed, we are done
            if (before.equals(current)) {
                break;
            }
        }
        return current;
    }

    /**
     * Performs a single calculation (e.g., '5*2') at a specific index.
     * Finds the left/right numbers, calculates the result, and rebuilds the string.
     * 
     * @param expr    The full expression string.
     * @param opIndex The index of the operator (e.g., '*').
     * @return The new expression string with the operation completed.
     */
    private static String performOperation(String expr, int opIndex) {
        char op = expr.charAt(opIndex);

        String leftNum = findLeftNumber(expr, opIndex - 1);

        String rightNumAndLength = findRightNumber(expr, opIndex + 1);
        String[] parts = rightNumAndLength.split(";");
        String rightNum = parts[0];
        int rightNumConsumedLength = Integer.parseInt(parts[1]);

        int lIndex = opIndex - leftNum.length();
        int rIndex = opIndex + 1 + rightNumConsumedLength;

        String result = calculate(leftNum, op, rightNum);

        String newExpr = expr.substring(0, lIndex) + result + expr.substring(rIndex);
        return newExpr;
    }

    /**
     * Helper function. Finds the full number (e.g., '-10' or '25') to the
     * left of an operator by searching backwards.
     * 
     * @param s          The expression string.
     * @param startIndex The index to start searching from (op_index - 1).
     * @return The found number as a string.
     */
    private static String findLeftNumber(String s, int startIndex) {
        StringBuilder num = new StringBuilder();
        for (int i = startIndex; i >= 0; i--) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num.insert(0, c);
            } else if (c == '-' && (i == 0 || "+-*/(".indexOf(s.charAt(i - 1)) != -1)) {
                num.insert(0, c);
                break;
            } else {

                break;
            }
        }
        return num.toString();
    }

    /**
     * Helper function. Finds the full number (e.g., '-3' or '+5') to the
     * right of an operator by searching forwards.
     * 
     * @param s          The expression string.
     * @param startIndex The index to start searching from (op_index + 1).
     * @return A string "number;length" (e.g., "-3;2") indicating the number
     *         and how many characters it consumed.
     */
    private static String findRightNumber(String s, int startIndex) {
        StringBuilder num = new StringBuilder();
        int consumedLength = 0;

        if (startIndex >= s.length()) {
            throw new StringIndexOutOfBoundsException("Expression ended unexpectedly");
        }

        char firstChar = s.charAt(startIndex);

        if (firstChar == '-' || firstChar == '+') {
            num.append(firstChar);
            startIndex++;
            consumedLength++;
        }

        for (int i = startIndex; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num.append(c);
                consumedLength++;
            } else {
                break;
            }
        }

        if (num.length() == 0 || (num.length() == 1 && (num.charAt(0) == '+' || num.charAt(0) == '-'))) {
            throw new NumberFormatException("No number found after operator");
        }

        return num.toString() + ";" + consumedLength;
    }

    /**
     * Performs the actual math on two numbers (as strings).
     * Handles basic arithmetic and throws an error for division by zero.
     * 
     * @param num1 Left number (e.g., "5").
     * @param op   Operator (e.g., '*').
     * @param num2 Right number (e.g., "-3").
     * @return The result as a string (e.g., "-15").
     */
    private static String calculate(String num1, char op, String num2) {
        long a = Long.parseLong(num1);
        long b = Long.parseLong(num2);

        switch (op) {
            case '*':
                return String.valueOf(a * b);
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return String.valueOf(a / b);
            case '+':
                return String.valueOf(a + b);
            case '-':
                return String.valueOf(a - b);
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    /**
     * Checks if the user's raw input is valid based on project rules.
     * This is the main safeguard against invalid expressions.
     * 
     * @param s The raw expression string from the user.
     * @return true if the expression is valid, false otherwise.
     */
    public static boolean isValidExpression(String s) {
        if (s == null || s.isEmpty())
            return false;
        String noSpaces = s.replaceAll("\\s+", "");
        if (noSpaces.isEmpty())
            return false;


        if (!noSpaces.matches("^[0-9()+\\-x:]+$"))
            return false;

        int balance = 0;
        for (char c : noSpaces.toCharArray()) {
            if (c == '(')
                balance++;
            else if (c == ')') {
                balance--;
                if (balance < 0)
                    return false;
            }
        }
        if (balance != 0)
            return false; 

        if (noSpaces.matches(".*[0-9]\\(.*") || noSpaces.matches(".*\\)[0-9].*") || noSpaces.contains(")("))
            return false;

        if (noSpaces.matches("^[+x:].*") || noSpaces.matches(".*[+\\-x:]$"))
            return false;

        if (noSpaces.matches(".*[+\\-x:]\\).*"))
            return false;

        if (noSpaces.contains("()"))
            return false;

        if (noSpaces.contains(":0")) {
            if (noSpaces.matches(".*:0($|[^0-9]).*"))
                return false;
        }

        if (noSpaces.contains("---"))
            return false;
        if (noSpaces.contains("+++"))
            return false;
        if (noSpaces.matches(".*[x:]{2,}.*"))
            return false;
        if (noSpaces.matches(".*[+\\-][x:].*"))
            return false;
        if (noSpaces.contains("++"))
            return false;
        if (noSpaces.matches(".*[x:][+].*"))
            return false;

        // Note: "--", "+-", "-+", "x-", "x+", ":-", ":+" are considered VALID
        // as they are handled by the evaluator (unary operators).

        return true;
    }

    /**
     * A simple cleanup utility. Simplifies '+-' to '-' and '-+' to '-'
     * after an operation rebuilds the string.
     * 
     * @param s The string to clean.
     * @return The cleaned string.
     */
    private static String tidy(String s) {
        String t = s;
        for (int i = 0; i < 2; i++) {
            t = t.replaceAll("\\+\\-", "-")
                    .replaceAll("\\-\\+", "-");
        }
        return t;
    }

    /**
     * Checks if a string is a single number (positive or negative).
     * Used to know when the evaluation is finished.
     * 
     * @param s The string to check.
     * @return true if the string is only a number, false otherwise.
     */
    private static boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return s.matches("^-?[0-9]+$");
    }

    /**
     * Converts the internal format ('*', '/') back to the
     * user-facing format ('x', ':') for printing.
     * 
     * @param s The internal expression string.
     * @return The "pretty" string for the user to see.
     */
    private static String prettyPrint(String s) {
        return s.replace('*', 'x').replace('/', ':');
    }

    // -------------------------------OPTION-C-HIGH-SCHOOL-------------------------------

    // -------------------------------OPTION-D-UNIVERSITY-------------------------------

    public static void connectFourMenu() {
        while (true) {
            System.out.println("=== D) University == Connect Four ===");
            System.out.println("[1] Start Game");
            System.out.println("[2] Return to Main Menu");
            System.out.print("Your choice: ");
            String s = sc.nextLine().trim();
            if ("1".equals(s)) {
                connectFourFlow();
                clearScreen();
            } else if ("2".equals(s)) {
                clearScreen();
                return; // Returns to main (and exits)
            } else {
                clearScreen();
                System.out.println("Invalid choice!");
            }
        }
    }

    public static void connectFourFlow() {
        clearScreen();
        System.out.println("Choose board size:");
        System.out.println("(1) 5x4  (2) 6x5  (3) 7x6");
        int ch = askInt("Choice: ", 1, 3);
        clearScreen();

        int rows, cols;
        if (ch == 1) {
            rows = 4;
            cols = 5;
        } else if (ch == 2) {
            rows = 5;
            cols = 6;
        } else {
            rows = 6;
            cols = 7;
        }

        int mode = 0;

        while (true) {
            System.out.println("Mode selection:");
            System.out.println("(1) Against Computer");
            System.out.println("(2) Player vs Player");
            System.out.print("Choice: ");
            String input = sc.nextLine().trim();

            if (input.equals("1")) {
                mode = 1;
                break;
            } else if (input.equals("2")) {
                mode = 2;
                break;
            } else {
                clearScreen();
                System.out.println("Invalid choice! Please choose 1 or 2.");
            }
        }

        clearScreen();

        char[][] board = initializeBoard(rows, cols);
        boolean p1Turn = true;

        while (true) {
            clearScreen();
            displayBoard(board);

            char disc;
            if (p1Turn)
                disc = 'X';
            else
                disc = 'O';

            boolean vsCpu = (mode == 1);

            if (p1Turn) {
                System.out.println("Player 1 (X) is on the move.");
            } else {
                if (vsCpu) {
                    System.out.println("CPU (O) is on the move.");
                    waitMs(1000);
                } else {
                    System.out.println("Player 2 (O) is on the move.");
                }
            }
            int col = -1;

            if (!p1Turn && vsCpu) {

                for (int c = 0; c < cols; c++) {
                    if (!isColumnFull(board, c)) {
                        col = c;
                        break;
                    }
                }

            } else {
                System.out.print("Column (1-" + cols + ") or Q (quit): ");
                String in = sc.nextLine().trim();

                if (in.equalsIgnoreCase("Q")) {
                    if (p1Turn)
                        System.out.println("Player 1 has left the game.");
                    else
                        System.out.println("Player 2 has left the game.");
                    break;
                }

                if (in.length() == 1 && in.charAt(0) >= '1' && in.charAt(0) <= ('0' + cols)) {
                    col = (in.charAt(0) - '0') - 1;
                    if (isColumnFull(board, col)) {
                        System.out.println("This column is full! Try another column.");
                        sleep(1000);
                        continue;
                    }
                } else {
                    System.out.println("Invalid entry. Please enter a number between 1-" + cols + ".");
                    sleep(1000);
                    continue;
                }
            }

            if (col != -1)
                dropDisc(board, col, disc);

            if (checkWinner(board, disc)) {
                clearScreen();
                displayBoard(board);
                if (p1Turn)
                    System.out.println("Player 1 (X) Wins!");
                else if (vsCpu)
                    System.out.println("CPU (O) Wins!");
                else
                    System.out.println("Player 2 (O) Wins!");
                pause();
                break;
            }

            if (isDraw(board)) {
                clearScreen();
                displayBoard(board);
                System.out.println("The board is full. It's a draw!");
                pause();
                break;
            }

            p1Turn = !p1Turn;
        }
    }

    /**
     * Creates a new game board of the given dimensions and fills it
     * with the ' ' (space) character.
     *
     * @param rows The number of rows for the board.
     * @param cols The number of columns for the board.
     * @return A new 2D char array (board) filled with spaces.
     */
    public static char[][] initializeBoard(int rows, int cols) {
        char[][] b = new char[rows][cols];
        for (int r = 0; r < rows; r++)
            Arrays.fill(b[r], ' ');
        return b;
    }

    /**
     * Renders the current state of the game board to the console,
     * including column numbers.
     *
     * @param board The 2D char array (board) to display.
     */
    public static void displayBoard(char[][] board) {
        int rows = board.length, cols = board[0].length;
        System.out.print("  ");
        for (int c = 1; c <= cols; c++)
            System.out.print(" " + c + "  ");
        System.out.println();
        for (int r = 0; r < rows; r++) {
            System.out.print(" |");
            for (int c = 0; c < cols; c++) {
                System.out.print(" " + board[r][c] + " |");
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int c = 0; c < cols; c++)
            System.out.print("--- ");
        System.out.println();
    }

    /**
     * Checks if a specific column on the board is full
     * (by checking the top row).
     *
     * @param board  The game board.
     * @param column The column index to check.
     * @return true if the column is full, false otherwise.
     */
    public static boolean isColumnFull(char[][] board, int column) {
        return board[0][column] != ' ';
    }

    /**
     * Drops a player's disc ('X' or 'O') into the selected column.
     * The disc settles in the lowest available row of that column.
     *
     * @param board  The game board.
     * @param column The column index to drop the disc into.
     * @param disc   The player's disc ('X' or 'O').
     */
    public static void dropDisc(char[][] board, int column, char disc) {
        for (int r = board.length - 1; r >= 0; r--) {
            if (board[r][column] == ' ') {
                board[r][column] = disc;
                return;
            }
        }
    }

    /**
     * Checks if the specified player (represented by their disc) has won the game.
     * Scans for horizontal, vertical, and diagonal fours.
     *
     * @param b The board (2D char array).
     * @param d The player's disc ('X' or 'O') to check for.
     * @return true if the player has won, false otherwise.
     */
    public static boolean checkWinner(char[][] b, char d) {
        int R = b.length, C = b[0].length;

        for (int r = 0; r < R; r++)
            for (int c = 0; c <= C - 4; c++)
                if (b[r][c] == d && b[r][c + 1] == d && b[r][c + 2] == d && b[r][c + 3] == d)
                    return true;

        for (int c = 0; c < C; c++)
            for (int r = 0; r <= R - 4; r++)
                if (b[r][c] == d && b[r + 1][c] == d && b[r + 2][c] == d && b[r + 3][c] == d)
                    return true;

        for (int r = 0; r <= R - 4; r++)
            for (int c = 0; c <= C - 4; c++)
                if (b[r][c] == d && b[r + 1][c + 1] == d && b[r + 2][c + 2] == d && b[r + 3][c + 3] == d)
                    return true;

        for (int r = 3; r < R; r++)
            for (int c = 0; c <= C - 4; c++)
                if (b[r][c] == d && b[r - 1][c + 1] == d && b[r - 2][c + 2] == d && b[r - 3][c + 3] == d)
                    return true;
        return false;
    }

    /**
     * Checks if the game is a draw (i.e., if the board is completely full).
     *
     * @param b The game board.
     * @return true if the board is full (draw), false otherwise.
     */
    public static boolean isDraw(char[][] b) {
        for (int c = 0; c < b[0].length; c++)
            if (b[0][c] == ' ')
                return false;
        return true;
    }

    /**
     * Determines the computer's (AI) next move.
     * (Note: This method is not called in the current 'connectFourFlow').
     *
     * @param board The game board.
     * @param me    The computer's disc ('O').
     * @param opp   The opponent's (human) disc ('X').
     * @param rng   A Random object for making random moves.
     * @return The column index the computer has chosen.
     */
    public static int computerMove(char[][] board, char me, char opp, Random rng) {
        int cols = board[0].length;

        for (int c = 0; c < cols; c++) {
            if (!isColumnFull(board, c) && leadsToWin(board, c, me)) {
                return c;
            }
        }

        for (int c = 0; c < cols; c++) {
            if (!isColumnFull(board, c) && leadsToWin(board, c, opp)) {
                return c;
            }
        }

        int center = cols / 2;
        for (int d = 0; d < cols; d++) {
            int left = center - d;
            if (left >= 0 && !isColumnFull(board, left))
                return left;

            int right = center + d;
            if (right < cols && !isColumnFull(board, right))
                return right;
        }

        int[] validCols = new int[cols];
        int count = 0;
        for (int c = 0; c < cols; c++) {
            if (!isColumnFull(board, c)) {
                validCols[count] = c;
                count++;
            }
        }
        if (count > 0) {
            int randomIndex = rng.nextInt(count);
            return validCols[randomIndex];
        }

        return 0;
    }

    /**
     * Simulates whether a specific move (dropping a disc in a column)
     * leads to a win.
     *
     * @param board The game board.
     * @param c     The column index to test.
     * @param disc  The player's disc to test.
     * @return true if this move results in a win, false otherwise.
     */
    public static boolean leadsToWin(char[][] board, int c, char disc) {
        int r = findDropRow(board, c);
        if (r == -1)
            return false; // Column is full
        board[r][c] = disc; // Make the move
        boolean win = checkWinner(board, disc);
        board[r][c] = ' '; // Undo the move
        return win;
    }

    /**
     * Finds the row where a disc would land if dropped in a specific column.
     *
     * @param board The game board.
     * @param c     The column index to check.
     * @return The row index where the disc would land. Returns -1 if the column is
     *         full.
     */
    public static int findDropRow(char[][] board, int c) {
        for (int r = board.length - 1; r >= 0; r--) {
            if (board[r][c] == ' ')
                return r;
        }
        return -1;
    }

    /**
     * Pauses program execution for a specified number of milliseconds
     * using a 'busy-wait' loop.
     *
     * @param ms The duration to wait (in milliseconds).
     */
    public static void waitMs(long ms) {
        long end = System.currentTimeMillis() + ms;
        while (System.currentTimeMillis() < end) {
            // busy waiting
        }
    }

    public static void pause() {
        System.out.print("Press Enter to continue...");
        sc.nextLine();
    }

    /**
     * Puts the current thread to sleep for the specified duration (using
     * Thread.sleep).
     *
     * @param ms The sleep duration (in milliseconds).
     */
    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {

        }
    }

    /**
     * Prompts the user to enter an integer within a specified range [min, max].
     * Keeps asking until valid input is received.
     *
     * @param prompt The message to display to the user (e.g., "Choice: ").
     * @param min    The minimum acceptable value.
     * @param max    The maximum acceptable value.
     * @return The valid integer entered by the user.
     */
    public static int askInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            boolean numeric = true;
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (ch < '0' || ch > '9') {
                    numeric = false;
                    break;
                }
            }

            if (!numeric || input.isEmpty()) {
                System.out.println("Incorrect entry. Please enter a number.");
                continue;
            }

            int value = 0;
            for (int i = 0; i < input.length(); i++) {
                value = value * 10 + (input.charAt(i) - '0');
            }

            if (value < min || value > max) {
                System.out.println("The number must be between " + min + " and " + max + ".");
                continue;
            }

            return value;
        }
    }
}