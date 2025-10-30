import java.lang.classfile.CodeBuilder.CatchBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class secondary {

    static String userInput = "";

    public static void main(String[] args) {
        clearConsole();
        System.out.println("Hello this is the option B");
        System.out.println("Type 'exit' to leave");
        System.out.println("-------------------------------");
        while (!userInput.equals("exit")) {
            try {
                System.out.println("Operation 1) Prime Numbers");
                System.out.println("Operation 2) Evoluation of Expression");
                System.out.print("Enter an operation: ");

                Scanner input = new Scanner(System.in);
                

                userInput = input.nextLine();

                switch (userInput) {
                    case "1", "Operation 1", "Prime Numbers":
                        int n;
                        Scanner scanner = new Scanner(System.in);
                        clearConsole();
                        System.out.println("You have selected Prime Numbers");
                        System.out.println("-------------------------------");
                        System.out.print("Enter a positive integer to find all prime numbers up to that number:");
                        n = scanner.nextInt();
                        System.out.println("Sieve of Eratosthenes Algorithm");
                        eratosthenes(n);
                        System.out.println("Sieve of Sundaram Algorithm");
                        sundaram(n);
                        System.out.println("Sieve of Atkin Algorithm");
                        atkin(n);
                        break;
                    case "2", "Operation 2", "Evoluation of Expression":
                        clearConsole();
                        System.out.println("You have selected Step-by-step Evaluation of Expression");
                        System.out.println("Enter an expression using digits and + - x : ( ). Type 'back' to return.");
                        System.out.println("-------------------------------");
                        while (true) {
                            System.out.print("expr> ");
                            String expr = input.nextLine();
                            if (expr == null) continue;
                            expr = expr.trim();
                            if (expr.equalsIgnoreCase("back")) {
                                System.out.println("-------------------------------");
                                break;
                            }
                            if (!isValid(expr)) {
                                System.out.println("re-enter a valid expression.");
                                continue;
                            }
                            try {
                                stepEvaluate(expr); // prints steps itself
                            } catch (Exception e) {
                                System.out.println("re-enter a valid expression.");
                            }
                        }
                        break;
                    case "exit":
                        clearConsole();
                        System.out.println("Exiting...");
                        input.close();
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
                
            } catch(Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        
    }

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

    public static void eratosthenes(int n) {
        boolean[] prime = new boolean[n+1];
        int primesInRange = 0;
        for (int i = 0; i <= n; i++) {
            if (i == 0 || i == 1) {
                prime[i] = false;
                continue;
            }
            prime[i] = true;
        }

        long startTime = System.nanoTime();
        for (int p = 2; p*p <= n; p++) {
            if (prime[p]) {
                for (int i = p*p; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        long endTime = System.nanoTime();

        for (int i = 0; i < prime.length; i++){
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
                }
                else if (prime[i] && (count == primesInRange - 2)){
                    System.out.print(i + ", ");
                }
                else if (prime[i] && (count == primesInRange - 1)) {
                    System.out.println(i + " ");
                }

                if (prime[i]){
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

    public static void sundaram(int n) {
        int nHalf = n/2;
        boolean[] marked = new boolean[nHalf + 1];
        long startTime = System.nanoTime();

        for (int i = 1; i <= nHalf; i++) {
            for (int j = i; ; j++){
                long index = (long)i + (long)j + (2L * i * j);
                
                if (index > nHalf) break;

                marked[(int)index] = true;
            }
        }
        
        List<Integer> primes = new ArrayList<>();
        primes.add(0, 2);
        for (int i = 1; i <= n/2; i++) {
            if (!marked[i]) {
                if (2* i + 1 == 2) {
                    continue;
                } else if (2 * i + 1 <= n)
                    primes.add((2 * i + 1));
            }
        }

        Long endTime = System.nanoTime();
        if(primes.size() >= 5){
            System.out.print(primes.get(0) + ", ");
            System.out.print(primes.get(1) + ", ");
            System.out.print(primes.get(2) + ", ");
            System.out.print(primes.get(primes.size()-2) + ", ");
            System.out.println(primes.get(primes.size()-1));
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

    public static void atkin(int n) {
        boolean[] marked = new boolean[n + 1];
        marked[2] = true;
        marked[3] = true;
        Long startTime = System.nanoTime();
        int primesInRange = 0;

        for (int x = 1; x*x <= n; x++){
            for (int y = 1; y*y <=n; y++) {

                //step 1
                long n1 = (4L*x*x) + (y*y);
                if (n1 <= n) {
                    if (n1 % 12 == 1 || n1 % 12 == 5) {
                        if(marked[(int)n1]) {
                            marked[(int)n1] = false;
                        } else {
                            marked[(int)n1] = true;
                        }
                    }
                }

                //step 2
                long n2 = (3L*x*x) + (y*y);
                if(n2 <= n) {
                    if (n2 % 12 == 7) {
                        if(marked[(int)n2]) {
                            marked[(int)n2] = false;
                        } else {
                            marked[(int)n2] = true;
                        }
                    }
                }

                //step 3
                if (x > y) {
                    long n3 = (3L*x*x) - (y*y);
                    if (n3 <= n) {
                        if (n3 % 12 == 11) {
                            if(marked[(int)n3]) {
                                marked[(int)n3] = false;
                            } else {
                                marked[(int)n3] = true;
                            }
                        }
                    }
                }
            }
        }

        for (int r = 5; (long)r * r <= n; r++) { 
            if (marked[r]) {
                long rSquare = (long)r * r;

                for (long i = rSquare; i <= n; i += rSquare) {
                
                    marked[(int)i] = false;
                }
            }
        }

        Long endTime = System.nanoTime();


        for (int i = 0; i < marked.length; i++) {
            if (marked[i]){
                primesInRange++;
            }
        }

        int count = 1;
        
        for (int i = 2; i <= n; i++) {
            if (marked[i] && count == primesInRange){
                System.out.println(i);
            } 
            else if (marked[i] && count <= 3) {
                System.out.print(i + ", ");
            }
            else if (marked[i] && count == (primesInRange-1)){
                System.out.print(i + ", ");
            }
            if (marked[i]){
                count++;
            }
        }
    


        System.out.println();
        Long totalTime = endTime - startTime;
        System.out.println("Time taken to compute primes up to " + n + ": " + totalTime + " nanoseconds");
        System.out.println("-------------------------------");
    }
    
    private static String lastPrinted = null;
    // ---------------- Option 2: EXPRESSION EVALUATOR ----------------
    // Entry point: prints every step (lines starting with "= ")
    private static void stepEvaluate(String raw) {
        String expr = normalizeForEval(raw);

        // 1) Parantezleri içten dışa indir ve adım yaz
        while (expr.contains("(")) {
            int r = expr.indexOf(')');
            if (r < 0) throw new RuntimeException("unbalanced");
            int l = expr.lastIndexOf('(', r);
            if (l < 0) throw new RuntimeException("unbalanced");

            String inner = expr.substring(l + 1, r);
            String reduced = reduceAll(inner);
            expr = tidy(expr.substring(0, l) + reduced + expr.substring(r + 1));
            printStep(expr); // veya System.out.println("= " + clean(pretty(expr)));
        }

        // 🔧 DÜZELTME B: parantezler bitince 0-<num> → -<num>
        expr = compressLeadingZeroMinus(expr);

        // 2) Çarpma/Bölme adımları
        expr = reduceWithSteps(expr, "([\\-]?\\d+)\\s*([*/])\\s*([\\-]?\\d+)");

        // 🔧 DÜZELTME B: çarpma/bölmeden sonra oluşabilecek 0-<num> kalıplarını temizle
        expr = compressLeadingZeroMinus(expr);

        // 3) Toplama/Çıkarma adımları
        expr = reduceWithSteps(expr, "([\\-]?\\d+)\\s*([+\\-])\\s*([\\-]?\\d+)");
    }


    private static String reduceWithSteps(String expr, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expr);
        while (m.find()) {
            long a = Long.parseLong(m.group(1));
            String op = m.group(2);
            long b = Long.parseLong(m.group(3));
            long res = switch (op) {
                case "*" -> a * b;
                case "/" -> {
                    if (b == 0) throw new ArithmeticException("div by zero");
                    yield a / b;
                }
                case "+" -> a + b;
                case "-" -> a - b;
                default -> throw new RuntimeException();
            };
            expr = expr.substring(0, m.start()) + res + expr.substring(m.end());
            expr = tidy(expr);
            printStep(expr);
            m = p.matcher(expr);
        }
        return expr;
    }

    // Parantez içini tamamen indir (adım yazmadan)
    private static String reduceAll(String expr) {
        expr = reduceNoPrint(expr, "([\\-]?\\d+)\\s*([*/])\\s*([\\-]?\\d+)");
        expr = reduceNoPrint(expr, "([\\-]?\\d+)\\s*([+\\-])\\s*([\\-]?\\d+)");
        return expr;
    }

    private static String reduceNoPrint(String expr, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expr);
        while (m.find()) {
            long a = Long.parseLong(m.group(1));
            String op = m.group(2);
            long b = Long.parseLong(m.group(3));
            long res = switch (op) {
                case "*" -> a * b;
                case "/" -> {
                    if (b == 0) throw new ArithmeticException("div by zero");
                    yield a / b;
                }
                case "+" -> a + b;
                case "-" -> a - b;
                default -> throw new RuntimeException();
            };
            expr = expr.substring(0, m.start()) + res + expr.substring(m.end());
            m = p.matcher(expr);
        }
        return tidy(expr);
    }

    // -------- Validation & helpers --------
    private static boolean isValid(String s) {
        if (s == null || s.isEmpty()) return false;
        String noSpaces = s.replaceAll("\\s+", "");
        if (!noSpaces.matches("[0-9()+\\-x:]+")) return false;

        // parantez dengesi
        int bal = 0;
        for (char c : noSpaces.toCharArray()) {
            if (c == '(') bal++;
            else if (c == ')') { bal--; if (bal < 0) return false; }
        }
        if (bal != 0) return false;

        // *** kritik değişiklik: tidy'siz normalizasyon ***
        String t = normalizeForValidation(s);

        // baş/son operatör kontrolü
        if (t.matches("^[+*/].*") || t.matches(".*[+\\-*/]$")) return false;

        // gizli çarpım yasak: ")(" veya "digit("
        if (t.contains(")(")) return false;
        for (int i = 0; i < t.length() - 1; i++) {
            char c1 = t.charAt(i), c2 = t.charAt(i + 1);
            if (Character.isDigit(c1) && c2 == '(') return false;
            if (c1 == ')' && Character.isDigit(c2)) return false;
        }

        // ARDIŞIK OPERATÖRLER: ++ kesinlikle yasak (örnek gereği)
        // Diğerleri de yakalanıyor; istersen '--'u serbest bırakabiliriz (aşağıdaki satırdaki -- kısmını silerek).
        if (t.matches(".*(\\+\\+|\\+\\*|\\+/|\\*\\+|/\\+|[+*/]{2,}|--{2,}).*")) return false;

        return true;
    }


        // --- NEW: validation-time normalize (NO tidy) ---
    private static String normalizeForValidation(String s) {
        String t = s.replace('x', '*').replace(':', '/');
        t = t.replaceAll("\\s+", "");
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (c == '-' && (i == 0 || t.charAt(i - 1) == '(')) {
                out.append("(0-");      // parantez aç
                // sayının geri kalanını da kapat
                int j = i + 1;
                while (j < t.length() && Character.isDigit(t.charAt(j))) {
                    out.append(t.charAt(j));
                    j++;
                }
                out.append(')');        // parantez kapat
                i = j - 1;              // döngüyü o kadar ilerlet
            } else {
                out.append(c);
            }

        }
        return out.toString(); // <- tidy YOK!
    }

    // mevcut normalize'ı sadece EVAL için kullanacağız
    private static String normalizeForEval(String s) {
        String t = s.replace('x', '*').replace(':', '/');
        t = t.replaceAll("\\s+", "");
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (c == '-' && (i == 0 || t.charAt(i - 1) == '(')) {
                out.append("(0-");      // parantez aç
                // sayının geri kalanını da kapat
                int j = i + 1;
                while (j < t.length() && Character.isDigit(t.charAt(j))) {
                    out.append(t.charAt(j));
                    j++;
                }
                out.append(')');        // parantez kapat
                i = j - 1;              // döngüyü o kadar ilerlet
            } else {
                out.append(c);
            }

        }
        return tidy(out.toString()); // <- tidy VAR
    }


    // +/− işaretlerini sadeleştir
    private static String tidy(String s) {
        String t = s;
        // birkaç kez geçerek basitleştir
        for (int i = 0; i < 3; i++) {
            t = t.replaceAll("\\+\\-", "-")
                 .replaceAll("\\-\\+", "-")
                 .replaceAll("\\+\\+", "+")
                 .replaceAll("--", "+");
        }
        return t;
    }

    // Ekrana x ve : ile yazdırma
    private static String pretty(String s) {
        return s.replace('*', 'x').replace('/', ':');
    }

    private static String clean(String s) {
    // Başta veya "(0-" şeklinde görünen 0'ları kaldırır
    return s.replaceAll("\\(0-", "(-").replaceAll("(^|[^0-9])0-", "$1-");
    }

    // yeni yazdırma metodu
    private static void printStep(String expr) {
        String out = "= " + clean(pretty(expr));
        if (!out.equals(lastPrinted)) {
            System.out.println(out);
            lastPrinted = out;
        }
    }

    // 0-<num> → -<num> dönüştürme (satır başında veya '(' sonrası)
    private static String compressLeadingZeroMinus(String s) {
        s = s.replaceAll("^0-(\\d+)", "-$1");      // satır başı: 0-10 -> -10
        s = s.replaceAll("\\(0-(\\d+)", "(-$1");   // parantez sonrası: (0-2 -> (-2
        return s;
    }


}

