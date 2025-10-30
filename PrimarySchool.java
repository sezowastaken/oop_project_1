import java.util.Scanner;


public class PrimarySchool{

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args){
        
        runPrimarySchool();
    }

    /** PrimarySchool menüsünü calıstırdık
     * Kullanıcı ana menüye dönenen kadar bu PrimarySchool menüsü calısacak
     */

     private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
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

    private static void AgeAndZodiacDetection(){

        clearScreen();
        System.out.println("\n---- Age and Zodiac Sign Detection ----");
        
        int day = askInt("Day of birth (1-31): ");
        int month = askInt("Month of birth (1-12): ");
        int year = askInt("Year of birth (exp: 1990): ");

        if(!isValidDate(year, month, day)){
            System.out.println("Invalid date! Please try again. ");
            return;
        }

        System.out.println("Please Enter a today's date:");
        int currentDay = askInt("Current day (1-31): ");
        int currentMonth = askInt("Current month (1-12): ");
        int currentYear = askInt("Current year (e.g. 2025): ");
        

        calculateAge(day, month, year, currentDay, currentMonth, currentYear);
        // hesaplamayı yapan metoda yönlendirme işlemi , bu metot sonucunda ekrana yazacağız
        repeatOrReturn(PrimarySchool::AgeAndZodiacDetection);
    }

    private static int askInt(String message){
        while (true){
            try {
                System.out.print(message);
                return Integer.parseInt(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
    // ayın 1-12 arasında olup olmadığını ve günün 1-31 arasında olup olmadığını kontrol eder.
    private static boolean isValidDate(int year, int month, int day){
        if (month < 1 || month > 12 || day < 1 || day >31)
        return false;

        int daysInMonth = calculateDaysInMonth(month, year);
        return day <= daysInMonth;
        // şubat 29-28 farkını calculateDaysInMonth kullanarak hesaplarız.
    }

    /* 1) Eğer doğum günü bugünkü günden büyükse, önceki aydan ödünç gün alırız önceki aydan 1 azaltırız.
     * 2) Eğer doğum ayı bugünkü aydan büyükse, önceki yıldan ödünç ay alırnır , yıl 1 azaltılır ay 12 artar
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

            // Bugün 15 ise doğum günü 25 ise bugünden doğum günü çıkmaz bir önceki aydan bir azaltırız ve o ayı gün olarak ekleriz
        if (birthDay > day) {
            month -= 1;

            if (month == 0){
                month = 12;
                year -=1;
                 // eğer ay 0 ise 12.aya gideriz ve yılı bir azaltırız
            }
            day += calculateDaysInMonth(month, year);  // önceki ayın gün sayısınını ekleriz 
        }

        if (birthMonth > month){
            month +=12;  // ay değerine 12 ay ekleriz
            year -=1;    // yıldan 1 yıl azaltırız
            // Doğum ayı bugünden büyükse ödünç ay alırız 
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

    private static int calculateDaysInMonth(int month, int year){

        switch (month){

            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;

            case 4: case 6: case 9: case 11:
                return 30;

            case 2:
            /* 4 yılda bir şubat 29 çekmektedir. Eğer yıl 400 ile tam bölünüyorsa artık yıl olmaktadır. 
             * Eğer yıl 100 ile tam bölünüyor fakat 400 e bölünmüyorsa artık yıl değildir.
             * Eğer yıl 4 ile tam bölünüyor fakat 100 e bölünmüyorsa artık yıldır.
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

        // boş null sentence kontrolü
        if (sentence == null || sentence.trim().isEmpty()){
            System.out.println("You entered an empty sentence. Returning to menu");
            return;
        }

        String NormalVersion = normalizeSpaces(sentence);
        String ReversedVersion = reverseWordsSymbols(NormalVersion);

        System.out.printf("Normal Version: %s%n", NormalVersion);
        System.out.printf("Reversed Version: %s%n", ReversedVersion);

        repeatOrReturn(PrimarySchool::AgeAndZodiacDetection);

    }

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

                // sadece 2 ve fazla harf içeren kelimeleri  ters çevirme
                if (word.length() >= 2){
                    out.append(reverseRecursive(word));
                }
                else{
                    out.append(word);  // tek harfli ise olduğu gibi bırakırız
                }
                i = j;
            }
            //noktalama işareti, boşluk , rakam olduğu yerde kalsın değişmesin
            else{
                out.append(character);
                i++;
            }
        }
        return out.toString();
    }

    private static String reverseRecursive(String s){
        if( s == null)
            return null;
        if( s.length() <= 1)
            return s;
        return reverseRecursive(s.substring(1)) + s.charAt(0);
    }

    private static String normalizeSpaces(String Text){

        return Text.trim().replaceAll("\\s+", " ");
        // Başta ve sonda olan boşlukları kaldırır, birden fazla boşluk varsa tek boşluğa indirir.
    }

    private static void repeatOrReturn(Runnable action) {
        while (true) {
            System.out.println("\nWhat do you want to do:");
            System.out.println("[1] Repeat the same operation");
            System.out.println("[2] Return to Primary School Menu");
            System.out.print("Your choice: ");
            String again = SC.nextLine().trim();
    
            switch (again) {
                case "1":
                    action.run();
                    return; // repeat ettikten sonra çıkma
                case "2":
                    return; // ana menüye dönme
                default:
                    System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
    }
    
}
