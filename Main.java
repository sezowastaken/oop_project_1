import java.util.*;

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

            String input = sc.nextLine().trim().toUpperCase(); // static sc kullanılıyor
            clearScreen();
            // kontrol: sadece 1 harf olmalı ve A-E arası olmalı

            if (input.length() == 1 && input.matches("[A-E]")) {
                char choice = input.charAt(0); // tek karakter al
                System.out.println("You chose: " + choice);
                // clearScreen(); // Hemen temizlemek yerine case içinde yapıldı

                switch (choice) {
                    case 'A':
                        clearScreen();
                        System.out.println("→ Primary Menu çalışıyor...");
                        pause(); // 4. Adım: Kullanılabilirlik için eklendi
                        clearScreen();
                        break;
                    case 'B':
                        clearScreen();
                        System.out.println("→ Secondary Menu çalışıyor...");
                        pause(); // 4. Adım: Kullanılabilirlik için eklendi
                        clearScreen();
                        break;
                    case 'C':
                        clearScreen();
                        System.out.println("→ High School Menu çalışıyor...");
                        pause(); // 4. Adım: Kullanılabilirlik için eklendi
                        clearScreen();
                        break;
                    case 'D':
                        clearScreen();
                        connectFourMenu();
                        clearScreen();
                        break;
                    case 'E':
                        System.out.println("Goodbye!");
                        sc.close(); // static sc kapatılıyor
                        return; // programı bitir
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

    // ===================================================================
    // 2. Adım: University.java'dan gelen tüm metotlar buraya kopyalandı
    // (main metodu hariç)
    // 'SC' olan tüm değişkenler 'sc' olarak düzeltildi.
    // ===================================================================

    /**
     * Displays the main menu for Connect Four.
     */
    public static void connectFourMenu() {
        while (true) {
            System.out.println("=== D) University == Connect Four ===");
            System.out.println("[1] Start Game");
            // Not: "Terminate" yerine "Return" daha mantıklı, çünkü 'return' ana menüye
            // döner.
            System.out.println("[2] Return to Main Menu");
            System.out.print("Your choice: ");
            String s = sc.nextLine().trim(); // sc kullanılıyor
            if ("1".equals(s)) {
                connectFourFlow();
                clearScreen();
            } else if ("2".equals(s)) {
                clearScreen();
                return; // Ana menüye (main) döner

            } else {
                clearScreen();
                System.out.println("Invalid choice!");
            }
        }
    }

    /**
     * Manages the main game flow.
     */
    public static void connectFourFlow() {
        clearScreen();
        System.out.println("Choose board size:");
        System.out.println("(1) 5x4  (2) 6x5  (3) 7x6");
        int ch = askInt("Choice: ", 1, 3); // sc kullanılıyor
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
            System.out.println("Mod selection:");
            System.out.println("(1) Against Computer");
            System.out.println("(2) Player vs Player");
            System.out.print("Choice: ");
            String input = sc.nextLine().trim(); // sc kullanılıyor

            if (input.equals("1")) {
                mode = 1;
                break;
            } else if (input.equals("2")) {
                mode = 2;
                break;
            } else {
                clearScreen();
                System.out.println("Invalid choice! Please choice 1 or 2.");
            }
        }

        clearScreen();

        char[][] board = initializeBoard(rows, cols);
        boolean p1Turn = true;
        // Not: AI (computerMove) metodu çağrılmıyor, form ve çalışma şekli korundu.
        // Random rng = new Random();

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
                    waitMs(1000); // Orijinal 'waitMs' korundu
                } else {
                    System.out.println("Player 2 (O) is on the move.");
                }
            }
            int col = -1;

            if (!p1Turn && vsCpu) {

                // Orijinal (basit) AI korundu:
                for (int c = 0; c < cols; c++) {
                    if (!isColumnFull(board, c)) {
                        col = c;
                        break;
                    }
                }

                // (Akıllı AI için: col = computerMove(board, 'O', 'X', rng);)

            } else {
                System.out.print("Column (1-" + cols + ") or Q (quit): ");
                String in = sc.nextLine().trim(); // sc kullanılıyor

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

                        continue;
                    }
                } else {
                    System.out.println("Invalid entry. Please 1-" + cols + " enter a number between.");

                    continue;
                }
            }

            if (col != -1)
                dropDisc(board, col, disc);

            if (checkWinner(board, disc)) {
                clearScreen();
                displayBoard(board);
                if (p1Turn)
                    System.out.println("Player 1 (X) Win!");
                else if (vsCpu)
                    System.out.println("Cpu (O) Win!");
                else
                    System.out.println("Player 2 (O) Win!");
                pause(); // sc kullanılıyor
                break;
            }

            if (isDraw(board)) {
                clearScreen();
                displayBoard(board);
                System.out.println("The board is full. It's a draw!");
                pause(); // sc kullanılıyor
                break;
            }

            p1Turn = !p1Turn;
        }

    }

    /**
     * Creates a new game board of the given dimensions.
     */
    public static char[][] initializeBoard(int rows, int cols) {
        char[][] b = new char[rows][cols];
        for (int r = 0; r < rows; r++)
            Arrays.fill(b[r], ' ');
        return b;
    }

    /**
     * Renders the current state of the game board.
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
     * Checks if a specific column on the board is full.
     */
    public static boolean isColumnFull(char[][] board, int column) {
        return board[0][column] != ' ';
    }

    /**
     * Drops a player's disc into the selected column.
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
     * Checks if the specified player (disc) has won the game.
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
     * Checks if the game is a draw (board is full).
     */
    public static boolean isDraw(char[][] b) {
        for (int c = 0; c < b[0].length; c++)
            if (b[0][c] == ' ')
                return false;
        return true;
    }

    /**
     * Determines the computer's next move (ORİJİNAL KODDA BU METOT
     * KULLANILMIYORDU).
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
     * Simulates whether a move leads to a win.
     */
    public static boolean leadsToWin(char[][] board, int c, char disc) {
        int r = findDropRow(board, c);
        if (r == -1)
            return false;
        board[r][c] = disc;
        boolean win = checkWinner(board, disc);
        board[r][c] = ' '; // undo
        return win;
    }

    /**
     * Finds the row where a disc would land.
     */
    public static int findDropRow(char[][] board, int c) {
        for (int r = board.length - 1; r >= 0; r--) {
            if (board[r][c] == ' ')
                return r;
        }
        return -1;
    }

    /**
     * Pauses program execution (busy-wait).
     * Orijinal form korundu.
     */
    public static void waitMs(long ms) {
        long end = System.currentTimeMillis() + ms;
        while (System.currentTimeMillis() < end) {
            // busy waiting
        }
    }

    /**
     * Pauses and waits for Enter key.
     */
    public static void pause() {
        System.out.print("Press Enter to continue...");
        sc.nextLine(); // static sc kullanılıyor
    }

    /**
     * Puts the current thread to sleep.
     */
    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Prompts the user to enter an integer within a range.
     * Orijinal form (manuel parsing) korundu.
     */
    public static int askInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim(); // static sc kullanılıyor

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
                System.out.println("Number " + min + " with " + max + " should be between.");
                continue;
            }

            return value;
        }
    }
}