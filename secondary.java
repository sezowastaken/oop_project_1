import java.lang.classfile.CodeBuilder.CatchBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                        System.out.println("You have selected Evoluation of Expression");
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

        System.out.println("There are " + primesInRange + " number of primes up to " + n + "\n");
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
        // todo tüm asal sayıları yazıyor, sadece ilk 3 ve sondan 2 asal sayıyı yazması lazım
        for (int i = 1; i <= n/2; i++) {
            if (!marked[i]) {
                if (2* i + 1 == 2) {
                    continue;
                } else if (2 * i + 1 <= n)
                    primes.add((2 * i + 1));
            }
        }

        Long endTime = System.nanoTime();
        System.out.print(primes.get(0) + ", ");
        System.out.print(primes.get(1) + ", ");
        System.out.print(primes.get(2) + ", ");
        System.out.print(primes.get(primes.size()-2) + ", ");
        System.out.println(primes.get(primes.size()-1));
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

        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (marked[i] && count < 3) {
                System.out.print(i + ", ");
            }
            else if (marked[i] && (count == primesInRange - 2)){
                System.out.print(i + ", ");
            }
            else if (marked[i] && (count == primesInRange - 1)) {
                System.out.println(i + " ");
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
}

