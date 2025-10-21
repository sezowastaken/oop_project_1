import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            Ascii.displayWelcomeMessage();
            System.out.println("=== MAIN MENU ===");
            System.out.println("[A] Primary School");
            System.out.println("[B] Secondary School");
            System.out.println("[C] High School");
            System.out.println("[D] University");
            System.out.println("[E] Terminate");
            System.out.print("Choose: ");

            String input = sc.nextLine().trim().toUpperCase(); // kullanıcı ne yazdı?

            // kontrol: sadece 1 harf olmalı ve A-E arası olmalı
            if (input.length() == 1 && input.matches("[A-E]")) {
                char choice = input.charAt(0); // tek karakter al
                System.out.println("You chose: " + choice);

                switch (choice) {
                    case 'A':
                        System.out.println("→ Primary Menu çalışıyor...");
                        break;
                    case 'B':
                        System.out.println("→ Secondary Menu çalışıyor...");
                        break;
                    case 'C':
                        System.out.println("→ High School Menu çalışıyor...");
                        break;
                    case 'D':
                        System.out.println("→ University Menu çalışıyor...");
                        break;
                    case 'E':
                        System.out.println("Goodbye!");
                        sc.close();
                        return; // programı bitir
                }
            } else {
                System.out.println("⚠️  Lütfen sadece A, B, C, D veya E harflerinden birini girin!\n");
            }
        }
    }
}
