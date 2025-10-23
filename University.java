import java.util.*;

/**
 * CMPE343 – Project #1 – Section D (University): Connect Four (Console)
 * - Board sizes: 5x4, 6x5, 7x6
 * - Modes: Single-player (vs Computer) or Two-players
 * - Features: input validation, clear screen, quit, win/draw detection, simple
 * AI
 */
public class University {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        connectFourMenu();
        System.out.println("Çıkılıyor. Görüşmek üzere!");
    }

    /** University submenu (single section run) */
    public static void connectFourMenu() {
        while (true) {
            clearScreen();
            System.out.println("=== D) University == Connect Four ===");
            System.out.println("[1] Start Game");
            System.out.println("[2] Terminate");
            System.out.print("Your choice: ");
            String s = SC.nextLine().trim();
            if ("1".equals(s)) {
                connectFourFlow();
                clearScreen();
            } else if ("2".equals(s)) {
                clearScreen();
                return;

            } else {
                System.out.println("Invalid choice!");
                pause();
            }
        }
    }

    /** Main flow: size & mode selection, loop of turns, result */
    public static void connectFourFlow() {
        clearScreen();
        System.out.println("Choose board size:");
        System.out.println("(1) 5x4   (2) 6x5   (3) 7x6");
        int ch = askInt("Choice: ", 1, 3);
        clearScreen();

        // Not: Problem metninde 5×4, 6×5, 7×6 sırasıyla (cols × rows) hissi verebilir.
        // Biz satır=yükseklik, sütun=genişlik olarak aşağıdaki gibi tanımlarız:
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

        int mode = 0; // başlangıçta geçersiz

        while (true) {
            System.out.println("\nMod selection:");
            System.out.println("(1) Against Computer");
            System.out.println("(2) Player vs Player");
            System.out.print("Choice: ");
            String input = SC.nextLine().trim();

            // sadece tek karakter ve 1–2 arası kontrolü
            if (input.equals("1")) {
                mode = 1;
                break; // geçerli seçim, döngüden çık
            } else if (input.equals("2")) {
                mode = 2;
                break;
            } else {
                System.out.println("Invalid choice! Please choice 1 or 2.\n");
            }
        }

        clearScreen();

        // tahta ve değişkenlerin hazırlanması
        char[][] board = initializeBoard(rows, cols);
        boolean p1Turn = true; // Player1 = 'X', Player2/CPU = 'O'

        while (true) {
            clearScreen();
            displayBoard(board);

            // Sıradaki taş
            char disc;
            if (p1Turn)
                disc = 'X';
            else
                disc = 'O';

            // Mod (1 ise CPU var)
            boolean vsCpu = (mode == 1);

            // Kimin sırası?
            if (p1Turn) {
                System.out.println("Oyuncu 1 (X) hamlede.");
            } else {
                if (vsCpu) {
                    System.out.println("Bilgisayar (O) hamlede.");
                    waitMs(1000); // 2 saniye bekle
                } else {
                    System.out.println("Oyuncu 2 (O) hamlede.");
                }
            }
            int col = -1; // 0-based sütun

            if (!p1Turn && vsCpu) {
                // === BASİT BİLGİSAYAR: soldan ilk boş sütunu seç ===
                for (int c = 0; c < cols; c++) {
                    if (!isColumnFull(board, c)) {
                        col = c;
                        break;
                    }
                }
                // (col == -1) ise tüm sütunlar doludur; berabere kontrolü aşağıda yapılır.
            } else {
                // === OYUNCU GİRİŞİ ===
                System.out.print("Sütun (1-" + cols + ") veya Q (vazgeç): ");
                String in = SC.nextLine().trim();

                // Çekilmek isterse
                if (in.equalsIgnoreCase("Q")) {
                    if (p1Turn)
                        System.out.println("Oyuncu 1 oyundan çekildi.");
                    else
                        System.out.println("Oyuncu 2 oyundan çekildi.");
                    pause();
                    break; // oyunu bitir
                }

                // Tek karakter ve 1..cols aralığı mı?
                if (in.length() == 1 && in.charAt(0) >= '1' && in.charAt(0) <= ('0' + cols)) {
                    col = (in.charAt(0) - '0') - 1; // 0-based
                    if (isColumnFull(board, col)) {
                        System.out.println("Bu sütun dolu! Başka bir sütun deneyin.");
                        pause();
                        continue;
                    }
                } else {
                    System.out.println("Geçersiz giriş. Lütfen 1-" + cols + " arasında bir sayı girin.");
                    pause();
                    continue;
                }
            }

            // Hamleyi uygula
            if (col != -1)
                dropDisc(board, col, disc);

            // Kazanma / Berabere
            if (checkWinner(board, disc)) {
                clearScreen();
                displayBoard(board);
                if (p1Turn)
                    System.out.println("Player 1 (X) Win!");
                else if (vsCpu)
                    System.out.println("Cpu (O) Win!");
                else
                    System.out.println("Player 2 (O) Win!");
                pause();
                break;
            }

            if (isDraw(board)) {
                clearScreen();
                displayBoard(board);
                System.out.println("Tahta doldu. Berabere!");
                pause();
                break;
            }

            p1Turn = !p1Turn;
        }

    }

    // ========= Board Utilities =========

    /** Create an empty board filled with spaces. */
    public static char[][] initializeBoard(int rows, int cols) {
        char[][] b = new char[rows][cols];
        for (int r = 0; r < rows; r++)
            Arrays.fill(b[r], ' ');
        return b;
    }

    /** Render the board with column headers. */
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

    /** True if top cell in column is not empty (column full). */
    public static boolean isColumnFull(char[][] board, int column) {
        return board[0][column] != ' ';
    }

    /** Drop disc into the lowest empty row of the selected column. */
    public static void dropDisc(char[][] board, int column, char disc) {
        for (int r = board.length - 1; r >= 0; r--) {
            if (board[r][column] == ' ') {
                board[r][column] = disc;
                return;
            }
        }
    }

    /** Check winner for 4 in a row (horizontal, vertical, 2 diagonals). */
    public static boolean checkWinner(char[][] b, char d) {
        int R = b.length, C = b[0].length;
        // horizontal
        for (int r = 0; r < R; r++)
            for (int c = 0; c <= C - 4; c++)
                if (b[r][c] == d && b[r][c + 1] == d && b[r][c + 2] == d && b[r][c + 3] == d)
                    return true;
        // vertical
        for (int c = 0; c < C; c++)
            for (int r = 0; r <= R - 4; r++)
                if (b[r][c] == d && b[r + 1][c] == d && b[r + 2][c] == d && b[r + 3][c] == d)
                    return true;
        // diagonal down-right
        for (int r = 0; r <= R - 4; r++)
            for (int c = 0; c <= C - 4; c++)
                if (b[r][c] == d && b[r + 1][c + 1] == d && b[r + 2][c + 2] == d && b[r + 3][c + 3] == d)
                    return true;
        // diagonal up-right
        for (int r = 3; r < R; r++)
            for (int c = 0; c <= C - 4; c++)
                if (b[r][c] == d && b[r - 1][c + 1] == d && b[r - 2][c + 2] == d && b[r - 3][c + 3] == d)
                    return true;
        return false;
    }

    /** True if no empty cell remains in the top row (i.e., all columns full). */
    public static boolean isDraw(char[][] b) {
        for (int c = 0; c < b[0].length; c++)
            if (b[0][c] == ' ')
                return false;
        return true;
    }

    // ========= Simple AI (Win/Block/Center/Random) =========

    /**
     * Returns a column index for computer:
     * 1) If it can win now, do it.
     * 2) Else if player can win next, block it.
     * 3) Else prefer center-most valid columns.
     * 4) Else random among valid.
     */
    public static int computerMove(char[][] board, char me, char opp, Random rng) {
        int cols = board[0].length;

        // 1) Kazanma hamlesi: kendi taşını bırakıp kazanabiliyor musun?
        for (int c = 0; c < cols; c++) {
            if (!isColumnFull(board, c) && leadsToWin(board, c, me)) {
                return c;
            }
        }

        // 2) Engelleme hamlesi: rakip bir sütuna oynarsa kazanır mı?
        for (int c = 0; c < cols; c++) {
            if (!isColumnFull(board, c) && leadsToWin(board, c, opp)) {
                return c;
            }
        }

        // 3) Merkeze yakın sütunu tercih et (0 tabanlı)
        int center = cols / 2;
        for (int d = 0; d < cols; d++) {
            int left = center - d;
            if (left >= 0 && !isColumnFull(board, left))
                return left;

            int right = center + d;
            if (right < cols && !isColumnFull(board, right))
                return right;
        }

        // 4) Rastgele geçerli sütun seç (diziyle)
        int[] validCols = new int[cols];
        int count = 0;

        for (int c = 0; c < cols; c++) {
            if (!isColumnFull(board, c)) {
                validCols[count] = c; // dizide sakla
                count++;
            }
        }

        if (count > 0) {
            int randomIndex = rng.nextInt(count); // sadece geçerli sütunlar arasından
            return validCols[randomIndex];
        }

        // Hiç geçerli sütun yoksa (tahta doluysa)
        return 0;
    }

    /** Simulate dropping 'disc' into column c and check if that makes a win. */
    public static boolean leadsToWin(char[][] board, int c, char disc) {
        int r = findDropRow(board, c);
        if (r == -1)
            return false;
        board[r][c] = disc;
        boolean win = checkWinner(board, disc);
        board[r][c] = ' '; // undo
        return win;
    }

    /** Find the row index where a disc would land in column c; -1 if full. */
    public static int findDropRow(char[][] board, int c) {
        for (int r = board.length - 1; r >= 0; r--) {
            if (board[r][c] == ' ')
                return r;
        }
        return -1;
    }

    // ========= Console helpers =========
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void waitMs(long ms) {
        long end = System.currentTimeMillis() + ms;
        while (System.currentTimeMillis() < end) {
            // bekleme (boş döngü)
        }
    }

    public static void pause() {
        System.out.print("Devam etmek için Enter'a basın...");
        SC.nextLine();
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    public static int askInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = SC.nextLine().trim();

            // Sayı mı kontrolü (negatifleri de desteklemek istersen '-' kontrolü
            // eklenebilir)
            boolean numeric = true;
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (ch < '0' || ch > '9') {
                    numeric = false;
                    break;
                }
            }

            if (!numeric || input.isEmpty()) {
                System.out.println("Hatalı giriş. Lütfen bir sayı girin.");
                continue; // tekrar sor
            }

            // String'i sayıya çevir (Integer.parseInt yerine elle)
            int value = 0;
            for (int i = 0; i < input.length(); i++) {
                value = value * 10 + (input.charAt(i) - '0');
            }

            // Aralık kontrolü
            if (value < min || value > max) {
                System.out.println("Sayı " + min + " ile " + max + " arasında olmalı.");
                continue;
            }

            return value; // geçerli giriş
        }
    }

}
