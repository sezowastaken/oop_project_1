import java.time.LocalDate;
import java.util.Scanner;


public class PrimarySchool{

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args){
        
        runPrimarySchool();
    }

    /** PrimarySchool menüsünü calıstırdık
     * Kullanıcı ana menüye dönenen kadar bu PrimarySchool menüsü calısacak
     */

    public static void runPrimarySchool(){

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
        System.out.println("\n---- Age and Zodiac Sign Detection ----");
        
        int day = askInt("Day of birth (1-31): ");
        int month = askInt("Month of birth (1-12): ");
        int year = askInt("Year of birth (exp: 1990): ");

        if(!isValidDate(year, month, day)){
            System.out.println("Invalid date! Please try again. ");
            return;
        }

        LocalDate today = LocalDate.now();
        int currentDay = today.getDayOfMonth();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        calculateAge(day, month, year, currentDay, currentMonth, currentYear);
        // hesaplamayı yapan metoda yönlendirme işlemi , bu metot sonucunda ekrana yazacağız
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

    /* 1) Eğer doğum günü bugünkü günden büyükse, gün "ödünç" alınır: önceki aydan
     *    o ayın gün sayısı eklenir ve ay 1 azaltılır.
     * 2) Eğer doğum ayı bugünkü aydan büyükse, ay "ödünç" alınır: yılı 1 azaltıp
     *    aya 12 eklenir. */

    private static void calculateAge(int birthdDay, int birthMonth, int birthYear, int currentDay, int currentMonth, int currentYear) {

        int day = currentDay;
        int month = currentMonth;
        int year = currentYear;

            // Bugün 15 ise doğum günü 25 ise bugünden doğum günü çıkmaz bir önceki aydan bir azaltırız ve o ayı gün olarak ekleriz
        if (birthdDay > day) {
            month -= 1;

            if (month == 0){
                month = 12;
                year -=1;
                 // eğer ay 0 ise 12.aya gideriz ve yılı bir azaltırız
            }
            day += calculateDaysInMonth(month, year);
            // önceki ayın gün sayısınını ekleriz 
        }

        if (birthdDay > month){
            month +=12;  // ay değerine 12 ay ekleriz
            year -=1;    // yıldan 1 yıl azaltırız

            // Doğum ayı bugünden büyükse ödünç ay alırız 
        }

        int years = year - birthYear;
        int months = month - birthMonth;    // kesin hesaplamaları yap
        int days = day - birthdDay;

        System.out.println("\n--------------------------");
        System.out.printf("Your age is: %d years, %d months, and %d days.%n",years, months, days);


        String zodiac = calculateZodiacSign(birthdDay, birthMonth);
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
        return "Unkown";
    }

    private static void ReverseTheWords(){

        System.out.println("\n----- Reverse the words ----");
        System.out.println("Please Enter a Sentence:");
        String sentence = SC.nextLine();

        String NormalVersion = normalizeSpaces(sentence);
        String ReversedVersion = reverseWords(NormalVersion);

        System.out.printf("Normal Version: %s%n", NormalVersion);
        System.out.printf("Reversed Version: %s%n", ReversedVersion);

    }

    private static String reverseWords(String Text){
        String[] words = Text.split(" ");

        // ters sırayla yeni bir stringbuilder oluşturuyoruz.
        StringBuilder reversed = new StringBuilder();

        // kelimeleri sondan başa doğru ekler
        for (int i = words.length - 1; i >= 0; i--){
            reversed.append(words[i]);
            if (i != 0){// son kelimeden sonra boşluk eklemek istemiyoruz.
                reversed.append(" ");
            }
        }
        return reversed.toString(); // StringBuilderi stringe çeviriyoruz.

    }

    private static String normalizeSpaces(String Text){

        return Text.trim().replaceAll("\\s+", " ");
        // Başta ve sonda olan boşlukları kaldırır, birden fazla boşluk varsa tek boşluğa indirir.
    }

   





}