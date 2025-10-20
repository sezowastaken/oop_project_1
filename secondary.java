import java.lang.classfile.CodeBuilder.CatchBuilder;
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
        long startTime = System.nanoTime();
        for (int i = 0; i <= n; i++) {
            if (i == 0 || i == 1) {
                prime[i] = false;
                continue;
            }
            prime[i] = true;
        }
        for (int p = 2; p*p <= n; p++) {
            if (prime[p]) {
                for (int i = p*p; i <= n; i += p) {
                    prime[i] = false;
                    primesInRange++;
                }
            }
        }
        long endTime = System.nanoTime();
        primesInRange = n - primesInRange;
        int primesInRangeTemp = primesInRange;

        System.out.println("Prime numbers up to " + n + ":");
        int count = 0;
        if (primesInRangeTemp > 5) {
            for (int i = 2; i <= n; i++) {
                if (prime[i]) {
                    System.out.print(i + ", ");
                    count++;
                }
                if (count == 3) {
                    break;
                }
            }
            for (int i = n; i >= 2; i--) {
                if (prime[i] && count == 4) {
                    System.out.println(i);
                    break;
                }
                if (prime[i]) {
                    System.out.print(i + ", ");
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
        
    }
}

