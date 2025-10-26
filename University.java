import java.util.*;

/**
 * CMPE343 – Project #1 – Section D (University): Connect Four (Console)
 * <p>
 * Implements a console-based Connect Four game.
 * <ul>
 * <li>Board sizes: 5x4, 6x5, 7x6</li>
 * <li>Modes: Single-player (vs Computer) or Two-players</li>
 * </ul>
 *
 * @author Tunahan Tuze
 */
public class University {

    private static final Scanner SC = new Scanner(System.in);

    /**
     * The main entry point for the program.
     * Runs the Connect Four menu and prints a farewell message upon exit.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        connectFourMenu();
        System.out.println("The programme is ending. See you soon!");
    }

    /**
     * Displays the main menu for Connect Four.
     * Allows the user to start the game or terminate the program.
     * Loops until the user chooses to terminate.
     */
    public static void connectFourMenu() {
        while (true) {
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
                clearScreen();
                System.out.println("Invalid choice!");
            }
        }
    }

    /**
     * Manages the main game flow.
     * This method covers:
     * 1. Board size selection.
     * 2. Game mode selection (vs Computer or Player vs Player).
     * 3. The main game loop (turns, moves, win/draw checks).
     * 4. Displaying the game end result.
     */
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
            System.out.println("Mod selection:");
            System.out.println("(1) Against Computer");
            System.out.println("(2) Player vs Player");
            System.out.print("Choice: ");
            String input = SC.nextLine().trim();

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
                String in = SC.nextLine().trim();

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
     * Creates a new game board of the given dimensions and
     * fills it with space characters (' ').
     *
     * @param rows The number of rows for the board.
     * @param cols The number of columns for the board.
     * @return A 2D char array representing the empty board.
     */
    public static char[][] initializeBoard(int rows, int cols) {
        char[][] b = new char[rows][cols];
        for (int r = 0; r < rows; r++)
            Arrays.fill(b[r], ' ');
        return b;
    }

    /**
     * Renders the current state of the game board to the console,
     * including column numbers and grid lines.
     *
     * @param board The 2D char array representing the game board.
     */
    public static void displayBoard(char[][] board) {
        int rows = board.length, cols = board[0].length;
        System.out.print("  ");
        for (int c = 1; c <= cols; c++)
            System.out.print(" " + c + "  ");
        System.out.println();
        for (int r = 0; r < rows; r++) {
            System.out.print(" |");
            for (int c = 0; c < cols; c++) {
                System.out.print(" " + board[r][c] + " |");
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int c = 0; c < cols; c++)
            System.out.print("--- ");
        System.out.println();
    }

    /**
     * Checks if a specific column on the board is full.
     *
     * @param board  The game board.
     * @param column The 0-based index of the column to check.
     * @return true if the column is full, false otherwise.
     */
    public static boolean isColumnFull(char[][] board, int column) {
        return board[0][column] != ' ';
    }

    /**
     * Drops a player's disc into the lowest available row of the selected column.
     *
     * @param board  The game board (will be modified by this method).
     * @param column The 0-based column index to drop the disc into.
     * @param disc   The character representing the player's disc ('X' or 'O').
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
     * Searches for four in a row horizontally, vertically, and on both diagonals.
     *
     * @param b The game board to check.
     * @param d The disc ('X' or 'O') to check for a win.
     * @return true if the specified player has won, false otherwise.
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
     * Checks if the game is a draw (i.e., the board is full).
     * This is done by checking if the top row (index 0) has any empty spaces.
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
     * Determines the computer's next move based on a simple AI strategy.
     * The strategy priority is:
     * 1. Win: If the computer (me) can win in one move.
     * 2. Block: If the opponent (opp) can win in one move.
     * 3. Center: Prefer columns closest to the center.
     * 4. Random: Pick a random valid column.
     *
     * @param board The current game board.
     * @param me    The computer's disc character ('O').
     * @param opp   The opponent's disc character ('X').
     * @param rng   A Random object for generating random numbers.
     * @return The 0-based column index for the computer's move.
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
            int randomIndex = rng.nextInt(count); // only among valid columns
            return validCols[randomIndex];
        }

        return 0;
    }

    /**
     * Simulates whether dropping a disc into a column will result in a win.
     * This method temporarily modifies the board and then undoes the move.
     *
     * @param board The game board to test.
     * @param c     The 0-based column index to test.
     * @param disc  The disc ('X' or 'O') to test.
     * @return true if this move results in a win, false otherwise.
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
     * Finds the top-most (lowest index) empty row where a disc would land in a
     * given column.
     *
     * @param board The game board to check.
     * @param c     The 0-based column index to check.
     * @return The 0-based row index where the disc would land, or -1 if the column
     *         is full.
     */
    public static int findDropRow(char[][] board, int c) {
        for (int r = board.length - 1; r >= 0; r--) {
            if (board[r][c] == ' ')
                return r;
        }
        return -1;
    }

    /**
     * Clears the console screen using ANSI escape codes.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pauses program execution for a specified number of milliseconds
     * using a busy-wait loop.
     *
     * @param ms The duration to wait in milliseconds.
     */
    public static void waitMs(long ms) {
        long end = System.currentTimeMillis() + ms;
        while (System.currentTimeMillis() < end) {
            // busy waiting
        }
    }

    /**
     * Pauses the program and waits for the user to press Enter to continue.
     */
    public static void pause() {
        System.out.print("Press Enter to continue...");
        SC.nextLine();
    }

    /**
     * 
     * Puts the current thread to sleep for a specified number of milliseconds.
     * Catches and ignores any InterruptedException.
     *
     * @param ms The sleep duration in milliseconds.
     */
    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Prompts the user to enter an integer within a specified range [min, max].
     * Keeps prompting until a valid number in the range is entered.
     *
     * @param prompt The message to display to the user.
     * @param min    The minimum acceptable integer value (inclusive).
     * @param max    The maximum acceptable integer value (inclusive).
     * @return The valid integer entered by the user.
     */
    public static int askInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = SC.nextLine().trim();

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