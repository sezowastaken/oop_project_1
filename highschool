import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		HighSchoolMenu(scanner);
	}

	public static void HighSchoolMenu(Scanner scanner)
	{
		while(true)
		{
			System.out.println("\n----- High School Menu -----");
			System.out.println("[1] Statical Information about an Array");
			System.out.println("[2] Distance between Two Arrays");
			System.out.println("[3] Return to Main Menu");
			System.out.print("Choose an option: ");
			String option = scanner.nextLine();

			switch(option)
			{
				case "1":
					staticinfoArray(scanner);
					break;
				case "2":
					distanceBetweenArrays(scanner);
					break;
				case "3":
					System.out.println("\nReturning to Main Menu...");
					return; //değişicek
				default:
					System.out.println("\nInvalid option. Please try again.");
					break;
			}
		}
	}
	//part 1
	public static void staticinfoArray(Scanner scanner)
	{
		// array oluşturma
		System.out.print("Enter the number of elements in the array: ");
		int n = Integer.parseInt(scanner.nextLine());

		double[] array = new double[n];

		for (int i = 0; i < n; i++)
		{
			System.out.print("Enter the element "+ (i + 1) + ": ");
			array[i] = Double.parseDouble(scanner.nextLine());
		}

		//medyan
		double median = calculateMedian(array);

		//aritmetik ortalama
		double arithmeticMean = calculateArithmeticMean(array);

		//geometrik ortalalam
		double geometricMean = calculateGeometricMean(array);

		//recursive harmonic mean
		double harmonicMean = calculateHarmonicMeanRecursive(array,array.length);

		//sonuçlar
		System.out.println("\nArray: " + Arrays.toString(array));
		System.out.println("Median: " + median);
		System.out.println("Arithmetic Mean: " + arithmeticMean);
		System.out.println("Geometric Mean: " + geometricMean);
		System.out.println("Harmonic Mean: " + harmonicMean);
	}

	public static double calculateMedian(double[] array)
	{
		int n = array.length;
		Arrays.sort(array);

		if (n % 2 == 1)
		{
			return array[n/2];
		}
		else
		{
			return (array[n/2-1] + array[n/2]) / 2.0;
		}
	}
	public static double calculateArithmeticMean(double[] array)
	{
		double sum = 0;
		for (double num : array)
		{
			sum += num;
		}
		return sum / array.length;
	}
	public static double calculateGeometricMean(double[] array)
	{
		double sum = 1.0;

		for (double num : array)
		{
			sum *= num;
		}
		return Math.pow(sum, 1.0 / array.length);
	}
	public static double calculateHarmonicMeanRecursive(double[] array,int n)
	{
		if (n == 1) {
			return array.length / (1.0 / array[0]);  // temel durum (base case)
		} else {
			return array.length / ((1.0 / array[n - 1]) + ((array.length - n + 1) / calculateHarmonicMeanRecursive(array, n - 1)));
		}
	}

	//part 2
	public static void distanceBetweenArrays(Scanner scanner)
	{
		System.out.print("Enter the arrays dimension: ");
		int n = Integer.parseInt(scanner.nextLine());

		int[] a = new int[n];
		int[] b = new int[n];

		System.out.println("Enter the elements of first array.");
		for (int i = 0; i < n; i++)
		{
			a[i] = validInteger(scanner,"Element "+(i+1)+":");
		}
		System.out.print("Enter the elements of second array: ");
		for (int i = 0; i < n; i++)
		{
			b[i] = validInteger(scanner,"Element "+(i+1)+":");
		}
		double manhattan = manhattanDistance(a,b);
		double euclidean = euclideanDistance(a,b);
		double cosinesim = cosineSimilarity(a,b);

		System.out.println("\n--- Distance and Similarity Results ---");
		System.out.printf("Manhattan Distance: %.3f\n", manhattan);
		System.out.printf("Euclidean Distance: %.3f\n", euclidean);
		System.out.printf("Cosine Similarity: %.3f\n", cosinesim);
	}

	public static int validInteger(Scanner scanner,String message)
	{
		while(true)
		{
			System.out.print(message);
			try {
				int num = Integer.parseInt(scanner.nextLine());
				if (num >= 0 && num <= 9)
				{
					return num;
				}
				else
				{
					System.out.println("\nInvalid input. Please enter between 0-9.");
				}
			}catch (NumberFormatException e) {
				System.out.println("\nInvalid input. Please enter a number.");
			}
		}
	}
	public static double manhattanDistance(int[] a, int[] b)
	{
		double distance = 0;
		for (int i = 0; i < a.length; i++)
		{
			distance += Math.abs(a[i] - b[i]);
		}
		return distance;
	}
	public static double euclideanDistance(int[] a, int[] b)
	{
		double distance = 0;
		for (int i = 0; i < a.length; i++)
		{
			distance += Math.pow(a[i] - b[i],2);
		}
		return Math.sqrt(distance);
	}
	public static double cosineSimilarity(int[] a, int[] b)
	{
		double sum = 0;
		double magA = 0;
		double magB = 0;

		for (int i = 0; i < a.length; i++)
		{
			sum += a[i] * b[i]; // a*b çarpım
			magA += Math.pow(a[i],2); // a üzeri 2 sadece büyüklük
			magB += Math.pow(b[i],2); // b üzeri 2 sadece büyüklük
		}

		return sum / (Math.sqrt(magA) + Math.sqrt(magB));
	}

}
