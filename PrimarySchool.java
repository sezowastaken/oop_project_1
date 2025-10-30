import java.util.Scanner;


/**
 * The PrimarySchool class provides two basic functions:
 * 1. Calculate the user's age and zodiac sign
 * 2. Reverse words in a sentence
 * 
 * The program works with a simple menu and keeps running 
 * until the user chooses to return to the main menu.
 */
public class PrimarySchool{

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args){
        
        runPrimarySchool();
    }

     private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Runs the Primary School main menu.
     * The user can choose from three options:
     * 1) Age and Zodiac Sign Detection
     * 2) Reverse the Words
     * 3) Back to Main Menu
     */
    public static void runPrimarySchool(){
        clearScreen();
       
        while (true){
            
            System.out.println("\n----- PRIMARY SCHOOL MENU -----");
            System.out.println("[1] Age and Zodiac Sign Detection");
            System.out.println("[2] Reverse the Words");
            System.out.println("[3] Back to Main Menu");
            System.out.print("-----------------------");
            System.out.print("Your choice: ");

            String choice = SC.nextLine().trim();

            switch (choice){

                case "1":
                    AgeAndZodiacDetection();
                    break;
                case "2":
                    ReverseTheWords();;
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
    private static void AgeAndZodiacDetection(){

        clearScreen();
        System.out.println("\n---- Age and Zodiac Sign Detection ----");
        
        int day = askInt("Day of birth (1-31): ",1, 31);
        int month = askInt("Month of birth (1-12): ",1, 12);
        int year = askInt("Year of birth (exp: 1990): ",1900, 2025);

        if(!isValidDate(year, month, day)){
            System.out.println("Invalid date! Please try again. ");
            return;
        }

        System.out.println("Please Enter a today's date:");
        int currentDay = askInt("Current day (1-31): ", 1, 31);
        int currentMonth = askInt("Current month (1-12): ", 1, 12);
        int currentYear = askInt("Current year (e.g. 2025): ", 1900, 2025);

        if (currentYear - year > 100) {
            System.out.println("Age cannot be greater than 100! Please try again.");
            return;
        }
        
        calculateAge(day, month, year, currentDay, currentMonth, currentYear);
        
        repeatOrReturn(PrimarySchool::AgeAndZodiacDetection);
    }

     /**
     * Asks the user to enter an integer and checks if it is valid.
     *
     * @param message The message shown to the user
     * @param min The minimum valid value
     * @param max The maximum valid value
     * @return The integer entered by the user
     */
    private static int askInt(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(SC.nextLine().trim());
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
     * @param year Year
     * @param month Month
     * @param day Day
     * @return True if the date is valid, false otherwise
     */
    
    private static boolean isValidDate(int year, int month, int day){
        if (month < 1 || month > 12 || day < 1 || day >31)  // Checks if the month is between 1-12 and the day is between 1-31.
        return false;

        int daysInMonth = calculateDaysInMonth(month, year);
        return day <= daysInMonth;
        // şubat 29-28 farkını calculateDaysInMonth kullanarak hesaplarız.
    }

    /* 1) If the birthdate is greater than today's day, we borrow days from the previous month and decrease the previous month by 1.
    2) If the birthdate is greater than today's month, we borrow months from the previous year, decrease the year by 1 and increase the month by 12.
    */ 
    
     /**
     * Calculates the user's age and zodiac sign based on birth date 
     * and today's date. Displays the results in years, months, and days.
     */
    private static void calculateAge(int birthDay, int birthMonth, int birthYear, int currentDay, int currentMonth, int currentYear) {

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

            if (month == 0){
                month = 12;
                year -=1;
            }
            day += calculateDaysInMonth(month, year);  
        }

        if (birthMonth > month){
            month +=12;  
            year -=1;    
        }

        int years = year - birthYear;
        int months = month - birthMonth;    
        int days = day - birthDay;

        System.out.println("\n--------------------------");
        System.out.printf("Your age is: %d years, %d months, and %d days.%n",years, months, days);

        String zodiac = calculateZodiacSign(birthDay, birthMonth);
        System.out.printf("Your zodiac sign is: %s%n", zodiac);
        System.out.println("---------------------");
        
    }

     /**
     * Calculates how many days are in a given month and year.
     * Accounts for leap years.
     *
     * @param month Month (1-12)
     * @param year Year
     * @return Number of days in the month
     */
    private static int calculateDaysInMonth(int month, int year){

        switch (month){

            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;

            case 4: case 6: case 9: case 11:
                return 30;

            case 2:
            /* February occurs on the 29th day every 4 years. If the year is divisible by 400, it is a leap year.
            * If the year is divisible by 100 but not by 400, it is not a leap year.
            * If the year is divisible by 4 but not by 100, it is a leap year.
            */
            if ((year % 400 == 0) || (year %4 == 0 && year % 100 != 0)){
                return 29;
            }
            else {
                return 28;
            }
            default:
            return 0;
        } 
    }
     /**
     * Determines the zodiac sign based on day and month.
     *
     * @param day Day
     * @param month Month
     * @return Zodiac sign as a string
     */

    private static String calculateZodiacSign(int day, int month){

        if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) return "Aquarius";
        if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) return "Pisces";
        if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) return "Aries";
        if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) return "Taurus";
        if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) return "Gemini";
        if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) return "Cancer";
        if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) return "Leo";
        if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) return "Virgo";
        if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) return "Libra";
        if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) return "Scorpio";
        if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) return "Sagittarius";
        if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) return "Capricorn";
        return "Unknown";
    }

    private static void ReverseTheWords(){
        clearScreen();

        System.out.println("\n----- Reverse the words ----");
        System.out.println("Please Enter a Sentence:");
        String sentence = SC.nextLine();

        if (sentence == null || sentence.trim().isEmpty()){
            System.out.println("You entered an empty sentence. Returning to menu");
            return;
        }

        String NormalVersion = normalizeSpaces(sentence);
        String ReversedVersion = reverseWordsSymbols(NormalVersion);

        System.out.printf("Normal Version: %s%n", NormalVersion);
        System.out.printf("Reversed Version: %s%n", ReversedVersion);

        repeatOrReturn(PrimarySchool::ReverseTheWords);

    }

       /**
     * Reverses words in a string. Words with 2 or more letters are reversed,
     * single letters remain the same.
     *
     * @param text Input text
     * @return Text with words reversed
     */
    private static String reverseWordsSymbols(String text){
        if(text == null || text.isEmpty())
            return text;
        StringBuilder out = new StringBuilder();
        int i = 0;
        int n = text.length();

        while (i < n){
            char character = text.charAt(i);
            if(Character.isLetter(character)){
                int j = i;
                while(j < n && Character.isLetter(text.charAt(j)))
                j++;
                String word = text.substring(i, j);

                if (word.length() >= 2){
                    out.append(reverseRecursive(word));
                }
                else{
                    out.append(word);  
                }
                i = j;
            }
            else{
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
    private static String reverseRecursive(String s){
        if( s == null)
            return null;
        if( s.length() <= 1)
            return s;
        return reverseRecursive(s.substring(1)) + s.charAt(0);
    }

     /**
     * Normalizes spaces in a string.
     * Trims leading and trailing spaces and replaces multiple spaces with a single space.
     *
     * @param Text Input text
     * @return Normalized text
     */
    private static String normalizeSpaces(String Text){

        return Text.trim().replaceAll("\\s+", " ");
       // Removes leading and trailing spaces, reduces multiple spaces to a single space.
    }

     /**
     * Gives the user the option to repeat an operation or return to the main menu.
     *
     * @param action The operation to repeat if the user chooses to repeat
     */
    private static void repeatOrReturn(Runnable action) {
        while (true) {
            System.out.println("\nWhat do you want to:");
            System.out.println("[1] Repeat the same operation");
            System.out.println("[2] Return to Primary School Menu");
            System.out.print("Your choice: ");
            String again = SC.nextLine().trim();
    
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
    
}
