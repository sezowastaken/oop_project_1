import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Ascii.displayWelcomeMessage();

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
                char choice = input.charAt(0);
                System.out.println("You chose: " + choice);
                // clearScreen();

                switch (choice) {
                    case 'A':
                        clearScreen();
                        runPrimarySchool();
                        clearScreen();
                        break;
                    case 'B':
                        clearScreen();
                        secondaryMenu();
                        clearScreen();
                        break;
                    case 'C':
                        clearScreen();
                        HighSchoolMenu();
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

    /**
     * Clears the terminal screen.
     * Uses 'cls' for Windows and 'clear' for Unix-based systems.
     */
    public static void clearScreen() {
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

    // -------------------------------OPTION-A-PRIMARY-SCHOOL-------------------------------

    /**
     * Runs the Primary School main menu.
     * The user can choose from three options:
     * 1) Age and Zodiac Sign Detection
     * 2) Reverse the Words
     * 3) Back to Main Menu
     */
    public static void runPrimarySchool() {

        while (true) {
            clearScreen();
            System.out.println("\n----- PRIMARY SCHOOL MENU -----");
            System.out.println("[1] Age and Zodiac Sign Detection");
            System.out.println("[2] Reverse the Words");
            System.out.println("[3] Back to Main Menu");
            System.out.print("-----------------------");
            System.out.print("Your choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1":
                    AgeAndZodiacDetection();
                    break;
                case "2":
                    ReverseTheWords();
                    ;
                    break;
                case "3":
                    System.out.println("Returning to the Main Menu...");
                    return;
                default:
                    System.out.println("Invalid selection! Please select one of 1-3.");

            }
        }
    }

    /**
     * Gets user's birth date and today's date, then calculates
     * the age and zodiac sign. Does not allow ages over 100 years.
     */
    private static void AgeAndZodiacDetection() {
        clearScreen();

        System.out.println("\n---- Age and Zodiac Sign Detection ----");

        int day = askIntA("Day of birth (1-31): ", 1, 31);
        int month = askIntA("Month of birth (1-12): ", 1, 12);
        int year = askIntA("Year of birth (exp: 1990): ", 1900, 2025);

        if (!isValidDate(year, month, day)) {
            System.out.println("Invalid date! Please try again. ");
            return;
        }

        System.out.println("Please Enter a today's date:");
        int currentDay = askIntA("Current day (1-31): ", 1, 31);
        int currentMonth = askIntA("Current month (1-12): ", 1, 12);
        int currentYear = askIntA("Current year (e.g. 2025): ", 1900, 2025);

        if (currentYear - year > 100) {
            System.out.println("Age cannot be greater than 100! Please try again.");
            return;
        }

        calculateAge(day, month, year, currentDay, currentMonth, currentYear);

        repeatOrReturn(Main::AgeAndZodiacDetection, "Primary School");
    }

    /**
     * Asks the user to enter an integer and checks if it is valid.
     *
     * @param message The message shown to the user
     * @param min     The minimum valid value
     * @param max     The maximum valid value
     * @return The integer entered by the user
     */
    private static int askIntA(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value < min || value > max) {
                    System.out.printf("Invalid range! Please enter a value between %d and %d.%n", min, max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    /**
     * Checks if the given year, month, and day form a valid date.
     *
     * @param year  Year
     * @param month Month
     * @param day   Day
     * @return True if the date is valid, false otherwise
     */

    private static boolean isValidDate(int year, int month, int day) {
        if (month < 1 || month > 12 || day < 1 || day > 31) // Checks if the month is between 1-12 and the day is
                                                            // between 1-31.
            return false;

        int daysInMonth = calculateDaysInMonth(month, year);
        return day <= daysInMonth;
        // şubat 29-28 farkını calculateDaysInMonth kullanarak hesaplarız.
    }

    /*
     * 1) If the birthdate is greater than today's day, we borrow days from the
     * previous month and decrease the previous month by 1.
     * 2) If the birthdate is greater than today's month, we borrow months from the
     * previous year, decrease the year by 1 and increase the month by 12.
     */

    /**
     * Calculates the user's age and zodiac sign based on birth date
     * and today's date. Displays the results in years, months, and days.
     */
    private static void calculateAge(int birthDay, int birthMonth, int birthYear, int currentDay, int currentMonth,
            int currentYear) {

        if (birthYear > currentYear ||
                (birthYear == currentYear && birthMonth > currentMonth) ||
                (birthYear == currentYear && birthMonth == currentMonth && birthDay > currentDay)) {
            System.out.println("You entered a future date. Returning to menu...");
            return;
        }

        int day = currentDay;
        int month = currentMonth;
        int year = currentYear;

        if (birthDay > day) {
            month -= 1;

            if (month == 0) {
                month = 12;
                year -= 1;
            }
            day += calculateDaysInMonth(month, year);
        }

        if (birthMonth > month) {
            month += 12;
            year -= 1;
        }

        int years = year - birthYear;
        int months = month - birthMonth;
        int days = day - birthDay;

        System.out.println("\n--------------------------");
        System.out.printf("Your age is: %d years, %d months, and %d days.%n", years, months, days);

        String zodiac = calculateZodiacSign(birthDay, birthMonth);
        System.out.printf("Your zodiac sign is: %s%n", zodiac);
        System.out.println("---------------------");

    }

    /**
     * Calculates how many days are in a given month and year.
     * Accounts for leap years.
     *
     * @param month Month (1-12)
     * @param year  Year
     * @return Number of days in the month
     */
    private static int calculateDaysInMonth(int month, int year) {

        switch (month) {

            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;

            case 4:
            case 6:
            case 9:
            case 11:
                return 30;

            case 2:
                /*
                 * February occurs on the 29th day every 4 years. If the year is divisible by
                 * 400, it is a leap year.
                 * If the year is divisible by 100 but not by 400, it is not a leap year.
                 * If the year is divisible by 4 but not by 100, it is a leap year.
                 */
                if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 0;
        }
    }

    /**
     * Determines the zodiac sign based on day and month.
     *
     * @param day   Day
     * @param month Month
     * @return Zodiac sign as a string
     */

    private static String calculateZodiacSign(int day, int month) {

        if ((month == 1 && day >= 20) || (month == 2 && day <= 18))
            return "Aquarius";
        if ((month == 2 && day >= 19) || (month == 3 && day <= 20))
            return "Pisces";
        if ((month == 3 && day >= 21) || (month == 4 && day <= 19))
            return "Aries";
        if ((month == 4 && day >= 20) || (month == 5 && day <= 20))
            return "Taurus";
        if ((month == 5 && day >= 21) || (month == 6 && day <= 20))
            return "Gemini";
        if ((month == 6 && day >= 21) || (month == 7 && day <= 22))
            return "Cancer";
        if ((month == 7 && day >= 23) || (month == 8 && day <= 22))
            return "Leo";
        if ((month == 8 && day >= 23) || (month == 9 && day <= 22))
            return "Virgo";
        if ((month == 9 && day >= 23) || (month == 10 && day <= 22))
            return "Libra";
        if ((month == 10 && day >= 23) || (month == 11 && day <= 21))
            return "Scorpio";
        if ((month == 11 && day >= 22) || (month == 12 && day <= 21))
            return "Sagittarius";
        if ((month == 12 && day >= 22) || (month == 1 && day <= 19))
            return "Capricorn";
        return "Unknown";
    }

    private static void ReverseTheWords() {
        clearScreen();

        System.out.println("\n----- Reverse the words ----");
        System.out.println("Please Enter a Sentence:");
        String sentence = sc.nextLine();

        if (sentence == null || sentence.trim().isEmpty()) {
            System.out.println("You entered an empty sentence. Returning to menu");
            return;
        }

        String NormalVersion = normalizeSpaces(sentence);
        String ReversedVersion = reverseWordsSymbols(NormalVersion);

        System.out.printf("Normal Version: %s%n", NormalVersion);
        System.out.printf("Reversed Version: %s%n", ReversedVersion);

        repeatOrReturn(Main::ReverseTheWords, "Primary School");

    }

    /**
     * Reverses words in a string. Words with 2 or more letters are reversed,
     * single letters remain the same.
     *
     * @param text Input text
     * @return Text with words reversed
     */
    private static String reverseWordsSymbols(String text) {
        if (text == null || text.isEmpty())
            return text;
        StringBuilder out = new StringBuilder();
        int i = 0;
        int n = text.length();

        while (i < n) {
            char character = text.charAt(i);
            if (Character.isLetter(character)) {
                int j = i;
                while (j < n && Character.isLetter(text.charAt(j)))
                    j++;
                String word = text.substring(i, j);

                if (word.length() >= 2) {
                    out.append(reverseRecursive(word));
                } else {
                    out.append(word);
                }
                i = j;
            } else {
                out.append(character);
                i++;
            }
        }
        return out.toString();
    }

    /**
     * Recursively reverses a single word.
     *
     * @param s Word
     * @return Reversed word
     */
    private static String reverseRecursive(String s) {
        if (s == null)
            return null;
        if (s.length() <= 1)
            return s;
        return reverseRecursive(s.substring(1)) + s.charAt(0);
    }

    /**
     * Normalizes spaces in a string.
     * Trims leading and trailing spaces and replaces multiple spaces with a single
     * space.
     *
     * @param Text Input text
     * @return Normalized text
     */
    private static String normalizeSpaces(String Text) {

        return Text.trim().replaceAll("\\s+", " ");
        // Removes leading and trailing spaces, reduces multiple spaces to a single
        // space.
    }

    /**
     * Gives the user the option to repeat an operation or return to a specified menu.
     * (Artık "menuName" parametresi alıyor)
     *
     * @param action The operation to repeat if the user chooses to repeat
     * @param menuName The name of the menu to return to (e.g., "Primary School")
     */
    private static void repeatOrReturn(Runnable action, String menuName) {
        while (true) {
            System.out.println("\nWhat do you want to:");
            System.out.println("[1] Repeat the same operation");
            System.out.printf("[2] Return to %s Menu%n", menuName);
            System.out.print("Your choice: ");
            String again = sc.nextLine().trim();

            switch (again) {
                case "1":
                    action.run();
                    return;
                case "2":
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
    }

    // -------------------------------OPTION-B-SECONDARY-SCHOOL-------------------------------

    static String userInput = "";

    public static void secondaryMenu() {
        userInput = "";
        clearScreen();
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
                        clearScreen();
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
                        clearScreen();
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option, please try again.");
                        pause();
                        clearScreen();
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
        clearScreen();
        System.out.println("You have selected Step-by-step Evaluation of Expression");
        System.out.println("Enter an expression using digits and + - x : ( ). Type 'back' to return.");
        System.out.println("-------------------------------");

        while (true) {
            System.out.print("expr> ");
            String expr = sc.nextLine();
            if (expr == null)
                continue;
            expr = expr.trim();
            if (expr.equalsIgnoreCase("back")) {
                clearScreen();
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
     * It evaluates one step, prints it, and then calls itself with the new
     * expression.
     * 
     * @param currentExpr The expression to be evaluated in this step.
     */
    private static void solveRecursively(String currentExpr) {

        // stop condition
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
     * 
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

    /**
     * High School Menu
     * Displays a menu with options to calculate statistical information about an
     * array or distance between two arrays.
     */
    public static void HighSchoolMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n----- High School Menu -----");
            System.out.println("[1] Statistical Information about an Array");
            System.out.println("[2] Distance between Two Arrays");
            System.out.println("[3] Return to Main Menu");
            System.out.print("Choose an option: ");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    staticinfoArray();
                    break;
                case "2":
                    distanceBetweenArrays();
                    break;
                case "3":
                    System.out.println("\nReturning to Main Menu...");
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.");
                    pause();
                    break;
            }
        }
    }

    /**
     * Statistical Information about an Array
     * Prompts the user for the array size and double elements.
     * Computes and prints: median, arithmetic mean, geometric mean, and harmonic
     * mean.
     * The harmonic mean is computed using a recursive reciprocal-sum helper. For
     * even-sized arrays, the median is the average of the two middle elements after
     * sorting.
     */
    public static void staticinfoArray() {
        int n = 0;
        while (true) {
            clearScreen();
            try {
                System.out.print("Enter the number of elements in the array: ");
                n = Integer.parseInt(sc.nextLine());
                if (n > 0) {
                    break;
                } else {
                    System.out.println("\nArray must be positive. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a positive integer.");
            }
        }

        double[] array = new double[n];

        System.out.println("For double values use dot (.) instead of comma (,) . eg: 1.5");

        for (int i = 0; i < n; i++) {
            while (true) {
                try {
                    System.out.print("Enter the element " + (i + 1) + ": ");
                    array[i] = Double.parseDouble(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid input. Please enter a valid number.");
                }
            }
        }

        double median = calculateMedian(array);

        double arithmeticMean = calculateArithmeticMean(array);

        double geometricMean = calculateGeometricMean(array);

        double harmonicMean = calculateHarmonicMean(array);

        System.out.println("\n--- Results ---");
        System.out.println("Array: " + Arrays.toString(array));
        System.out.println("Median: " + median);
        System.out.println("Arithmetic Mean: " + arithmeticMean);
        System.out.println("Geometric Mean: " + geometricMean);
        System.out.println("Harmonic Mean: " + harmonicMean);

        repeatOrReturn(Main::staticinfoArray, "High School");
    }

    /**
     * Calculate Median
     * Calculates the median of an array.
     * 
     * @param array {@link double[]} the array to calculate the median of
     * @return the median of the array
     */
    public static double calculateMedian(double[] array) {
        int n = array.length;
        Arrays.sort(array);

        if (n % 2 == 1) {
            return array[n / 2];
        } else {
            return (array[n / 2 - 1] + array[n / 2]) / 2.0;
        }
    }

    /**
     * Calculate Arithmetic Mean
     * Calculates the arithmetic mean of an array.
     * 
     * @param array {@link double[]} the array to calculate the arithmetic mean of
     * @return the arithmetic mean of the array
     */
    public static double calculateArithmeticMean(double[] array) {
        double sum = 0;
        for (double num : array) {
            sum += num;
        }
        return sum / array.length;
    }

    /**
     * Calculate Geometric Mean
     * Calculates the geometric mean of an array.
     * 
     * @param array {@link double[]} the array to calculate the geometric mean of
     * @return the geometric mean of the array
     */
    public static double calculateGeometricMean(double[] array) {
        double sum = 1.0;

        for (double num : array) {
            if (num <= 0) {
                System.out.println("\nGeometric mean cannot be calculated with non-positive values.");
                return 0;
            }
            sum *= num;
        }
        return Math.pow(sum, 1.0 / array.length);
    }

    /**
     * Calculate Harmonic Mean
     * Calculates the harmonic mean of an array.
     * 
     * @param array {@link double[]} the array to calculate the harmonic mean of
     * @return the harmonic mean of the array
     */
    public static double calculateHarmonicMean(double[] array) {
        if (array.length == 0)
            return 0.0;

        double sumOfReciprocals = calculateReciprocalSumRecursive(array, array.length - 1);

        if (sumOfReciprocals == 0) {
            return 0.0;
        }
        return array.length / sumOfReciprocals;
    }

    /**
     * Calculate Reciprocal Sum Recursively
     * Calculates the reciprocal sum of an array recursively.
     * 
     * @param array {@link double[]} the array to calculate the reciprocal sum of
     * @param index {@link int} the index to calculate the reciprocal sum of
     * @return the reciprocal sum of the array
     */
    public static double calculateReciprocalSumRecursive(double[] array, int index) {
        if (index < 0) {
            return 0.0;
        }

        if (array[index] == 0) {
            System.out.println("Warning: Zero element skipped for Harmonic Mean calculation.");
            return calculateReciprocalSumRecursive(array, index - 1);
        }

        double currentReciprocal = 1.0 / array[index];

        return currentReciprocal + calculateReciprocalSumRecursive(array, index - 1);
    }

    /**
     * Distance between Two Arrays
     * Computes the Cosine similarity between vectors a and b:
     * (a · b) / (||a|| * ||b||), where ||v|| = sqrt(Σ v_i^2).
     * Returns 0 if either vector has zero magnitude.
     *
     * @param a first integer vector
     * @param b second integer vector
     */
    public static void distanceBetweenArrays() {
        int n = 0;
        while (true) {
            clearScreen();
            try {
                System.out.print("Enter the arrays dimension: ");
                n = Integer.parseInt(sc.nextLine());
                if (n > 0) {
                    break;
                } else {
                    System.out.println("\nDimension must be positive. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid number.");
            }
        }

        int[] a = new int[n];
        int[] b = new int[n];

        System.out.println("Enter the elements of first array.");
        for (int i = 0; i < n; i++) {
            a[i] = validInteger("Element " + (i + 1) + ": ");
        }
        System.out.println("Enter the elements of second array: ");
        for (int i = 0; i < n; i++) {
            b[i] = validInteger("Element " + (i + 1) + ": ");
        }
        double manhattan = manhattanDistance(a, b);
        double euclidean = euclideanDistance(a, b);
        double cosinesim = cosineSimilarity(a, b);

        System.out.println("\n--- Distance and Similarity Results ---");
        System.out.println("Array 1: " + Arrays.toString(a));
        System.out.println("Array 2: " + Arrays.toString(b));
        System.out.printf("Manhattan Distance: %.3f\n", manhattan);
        System.out.printf("Euclidean Distance: %.3f\n", euclidean);
        System.out.printf("Cosine Similarity: %.3f\n", cosinesim);

        repeatOrReturn(Main::distanceBetweenArrays, "High School");
    }

    /**
     * Validate Integer
     * Validates an integer input.
     * 
     * @param message {@link String} the message to display
     * @return the validated integer
     */
    public static int validInteger(String message) {
        while (true) {
            System.out.print(message);
            try {
                int num = Integer.parseInt(sc.nextLine());
                if (num >= 0 && num <= 9) {
                    return num;
                } else {
                    System.out.println("\nInvalid input. Please enter between 0-9.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a number.");
            }
        }
    }

    /**
     * Calculate Manhattan Distance
     * Calculates the Manhattan distance between two arrays.
     * 
     * @param a {@link int[]} the first array
     * @param b {@link int[]} the second array
     * @return the Manhattan distance between the two arrays
     */
    public static double manhattanDistance(int[] a, int[] b) {
        double distance = 0;
        for (int i = 0; i < a.length; i++) {
            distance += Math.abs(a[i] - b[i]);
        }
        return distance;
    }

    /**
     * Calculate Euclidean Distance
     * Calculates the Euclidean distance between two arrays.
     * 
     * @param a {@link int[]} the first array
     * @param b {@link int[]} the second array
     * @return the Euclidean distance between the two arrays
     */
    public static double euclideanDistance(int[] a, int[] b) {
        double distance = 0;
        for (int i = 0; i < a.length; i++) {
            distance += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(distance);
    }

    /**
     * Calculate Cosine Similarity
     * Calculates the cosine similarity between two arrays.
     * 
     * @param a {@link int[]} the first array
     * @param b {@link int[]} the second array
     * @return the cosine similarity between the two arrays
     */
    public static double cosineSimilarity(int[] a, int[] b) {
        double sum = 0;
        double magA = 0;
        double magB = 0;

        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
            magA += Math.pow(a[i], 2);
            magB += Math.pow(b[i], 2);
        }

        if (magA == 0 || magB == 0) {
            return 0;
        }

        return sum / (Math.sqrt(magA) * Math.sqrt(magB));
    }

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
