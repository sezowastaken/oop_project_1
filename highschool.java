import java.util.Arrays;
import java.util.Scanner;

public class highschool {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		HighSchoolMenu(scanner);
	}
	/** High School Menu
	 * Displays a menu with options to calculate statistical information about an array or distance between two arrays.
	 * @param scanner {@link java.util.Scanner} for reading user input
	 */
	public static void HighSchoolMenu(Scanner scanner) {
		while (true) {
			System.out.println("\n----- High School Menu -----");
			System.out.println("[1] Statistical Information about an Array");
			System.out.println("[2] Distance between Two Arrays");
			System.out.println("[3] Return to Main Menu");
			System.out.print("Choose an option: ");
			String option = scanner.nextLine();

			switch (option) {
				case "1":
					staticinfoArray(scanner);
					break;
				case "2":
					distanceBetweenArrays(scanner);
					break;
				case "3":
					System.out.println("\nReturning to Main Menu...");
					return;
				default:
					System.out.println("\nInvalid option. Please try again.");
					break;
			}
		}
	}

	/** Statistical Information about an Array
	 * Prompts the user for the array size and double elements.
	 * Computes and prints: median, arithmetic mean, geometric mean, and harmonic mean.
	 * The harmonic mean is computed using a recursive reciprocal-sum helper. For even-sized arrays, the median is the average of the two middle elements after sorting.
	 * @param scanner {@link java.util.Scanner} for reading user input
	 */
	public static void staticinfoArray(Scanner scanner) {
		int n = 0;
		while (true) {
			try{
				System.out.print("Enter the number of elements in the array: ");
				n = Integer.parseInt(scanner.nextLine());
				if (n > 0)
				{
					break;
				}
				else {
					System.out.println("\nArray must be positive. Please try again.");
				}
				}catch (NumberFormatException e) {
				System.out.println("\nInvalid input. Please enter a positive integer.");
			}
		}

		double[] array = new double[n];

		System.out.println("For double values use dot (.) instead of comma (,) . eg: 1.5");

		for (int i = 0; i < n; i++) {
			while (true)
			{
				try {
					System.out.print("Enter the element " + (i + 1) + ": ");
					array[i] = Double.parseDouble(scanner.nextLine());
					break;
				}catch (NumberFormatException e) {
					System.out.println("\nInvalid input. Please enter a valid number.");
				}
			}
		}

		double median = calculateMedian(array);

		double arithmeticMean = calculateArithmeticMean(array);

		double geometricMean = calculateGeometricMean(array);

		double harmonicMean = calculateHarmonicMean(array);

		System.out.println("\n--- Results ---");
		System.out.println("Array: " + Arrays.toString(array));
		System.out.println("Median: " + median);
		System.out.println("Arithmetic Mean: " + arithmeticMean);
		System.out.println("Geometric Mean: " + geometricMean);
		System.out.println("Harmonic Mean: " + harmonicMean);
	}

	/** Calculate Median
	 * Calculates the median of an array.
	 * @param array {@link double[]} the array to calculate the median of
	 * @return the median of the array
	 */
	public static double calculateMedian(double[] array) {
		int n = array.length;
		Arrays.sort(array);

		if (n % 2 == 1) {
			return array[n / 2];
		} else {
			return (array[n / 2 - 1] + array[n / 2]) / 2.0;
		}
	}

	/** Calculate Arithmetic Mean
	 * Calculates the arithmetic mean of an array.
	 * @param array {@link double[]} the array to calculate the arithmetic mean of
	 * @return the arithmetic mean of the array
	 */
	public static double calculateArithmeticMean(double[] array) {
		double sum = 0;
		for (double num : array) {
			sum += num;
		}
		return sum / array.length;
	}

	/** Calculate Geometric Mean
	 * Calculates the geometric mean of an array.
	 * @param array {@link double[]} the array to calculate the geometric mean of
	 * @return the geometric mean of the array
	 */
	public static double calculateGeometricMean(double[] array) {
		double sum = 1.0;

		for (double num : array) {
			if (num <= 0){
				System.out.println("\nGeometric mean cannot be calculated with non-positive values.");
				return 0;
			}
			sum *= num;
		}
		return Math.pow(sum, 1.0 / array.length);
	}

	/** Calculate Harmonic Mean
	 * Calculates the harmonic mean of an array.
	 * @param array {@link double[]} the array to calculate the harmonic mean of
	 * @return the harmonic mean of the array
	 */
	public static double calculateHarmonicMean(double[] array)
    {
       if (array.length == 0) return 0.0;
       
       double sumOfReciprocals = calculateReciprocalSumRecursive(array, array.length - 1);
       
       if (sumOfReciprocals == 0) {
           return 0.0; 
       }
       return array.length / sumOfReciprocals;
    }
    
	/** Calculate Reciprocal Sum Recursively
	 * Calculates the reciprocal sum of an array recursively.
	 * @param array {@link double[]} the array to calculate the reciprocal sum of
	 * @param index {@link int} the index to calculate the reciprocal sum of
	 * @return the reciprocal sum of the array
	 */
    public static double calculateReciprocalSumRecursive(double[] array, int index)
    {
        if (index < 0) {
            return 0.0;
        }

        if (array[index] == 0) {
            System.out.println("Warning: Zero element skipped for Harmonic Mean calculation.");
            return calculateReciprocalSumRecursive(array, index - 1);
        }
        
        double currentReciprocal = 1.0 / array[index];
        
        return currentReciprocal + calculateReciprocalSumRecursive(array, index - 1);
    }

	/** Distance between Two Arrays
	 * Computes the Cosine similarity between vectors a and b:
	 * (a · b) / (||a|| * ||b||), where ||v|| = sqrt(Σ v_i^2).
	 * Returns 0 if either vector has zero magnitude.
	 *
	 * @param a first integer vector
	 * @param b second integer vector
	 * @return Cosine similarity in [-1, 1], or 0 if a or b has zero magnitude
	 */
	public static void distanceBetweenArrays(Scanner scanner) {
		int n = 0;
		while (true) {
			try {
				System.out.print("Enter the arrays dimension: ");
				n = Integer.parseInt(scanner.nextLine());
				if (n > 0){
					break;
				} else {
					System.out.println("\nDimension must be positive. Please try again.");
				}
			}catch (NumberFormatException e) {
				System.out.println("\nInvalid input. Please enter a valid number.");
			}
		}

		int[] a = new int[n];
		int[] b = new int[n];

		System.out.println("Enter the elements of first array.");
		for (int i = 0; i < n; i++) {
			a[i] = validInteger(scanner, "Element " + (i + 1) + ":");
		}
		System.out.print("Enter the elements of second array: ");
		for (int i = 0; i < n; i++) {
			b[i] = validInteger(scanner, "Element " + (i + 1) + ":");
		}
		double manhattan = manhattanDistance(a, b);
		double euclidean = euclideanDistance(a, b);
		double cosinesim = cosineSimilarity(a, b);

		System.out.println("\n--- Distance and Similarity Results ---");
		System.out.println("Array 1: " + Arrays.toString(a));
		System.out.println("Array 2: " + Arrays.toString(b));
		System.out.printf("Manhattan Distance: %.3f\n", manhattan);
		System.out.printf("Euclidean Distance: %.3f\n", euclidean);
		System.out.printf("Cosine Similarity: %.3f\n", cosinesim);
	}

	/** Validate Integer
	 * Validates an integer input.
	 * @param scanner {@link java.util.Scanner} for reading user input
	 * @param message {@link String} the message to display
	 * @return the validated integer
	 */
	public static int validInteger(Scanner scanner, String message) {
		while (true) {
			System.out.print(message);
			try {
				int num = Integer.parseInt(scanner.nextLine());
				if (num >= 0 && num <= 9) {
					return num;
				} else {
					System.out.println("\nInvalid input. Please enter between 0-9.");
				}
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid input. Please enter a number.");
			}
		}
	}

	/** Calculate Manhattan Distance
	 * Calculates the Manhattan distance between two arrays.
	 * @param a {@link int[]} the first array
	 * @param b {@link int[]} the second array
	 * @return the Manhattan distance between the two arrays
	 */
	public static double manhattanDistance(int[] a, int[] b) {
		double distance = 0;
		for (int i = 0; i < a.length; i++) {
			distance += Math.abs(a[i] - b[i]);
		}
		return distance;
	}

	/** Calculate Euclidean Distance
	 * Calculates the Euclidean distance between two arrays.
	 * @param a {@link int[]} the first array
	 * @param b {@link int[]} the second array
	 * @return the Euclidean distance between the two arrays
	 */
	public static double euclideanDistance(int[] a, int[] b) {
		double distance = 0;
		for (int i = 0; i < a.length; i++) {
			distance += Math.pow(a[i] - b[i], 2);
		}
		return Math.sqrt(distance);
	}

	/** Calculate Cosine Similarity
	 * Calculates the cosine similarity between two arrays.
	 * @param a {@link int[]} the first array
	 * @param b {@link int[]} the second array
	 * @return the cosine similarity between the two arrays
	 */
	public static double cosineSimilarity(int[] a, int[] b) {
		double sum = 0;
		double magA = 0; 
		double magB = 0; 

		for (int i = 0; i < a.length; i++) {
			sum += a[i] * b[i];
			magA += Math.pow(a[i], 2); 
			magB += Math.pow(b[i], 2);
		}

		if (magA == 0 || magB == 0) {
			return 0;
		}

		return sum / (Math.sqrt(magA) * Math.sqrt(magB));
	}

}