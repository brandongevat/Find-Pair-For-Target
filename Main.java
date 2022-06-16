/* COP 3503C Assignment 1
 * This program is written by: Brandon Gevat */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main{

	public static class Pair {
		int[] values = new int[2];

		public String toString() {
			return "(" + values[0] + ", " + values[1] + ")";
		}
	}

	public static void displayArray(int array[]) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}

	// Iterates through the given hashet and attempts to find a value of the difference of the target value
	// and the current value.
	static Pair hashSearch(HashSet<Integer> set, int target) {
		Pair ret = new Pair();

		Iterator iter = set.iterator();
		Integer currVal;

		while (iter.hasNext()) {
			currVal = Integer.parseInt(iter.next().toString());
			int difference = target - currVal.intValue(); 
			if (set.contains(difference)) {
				ret.values[0] = currVal.intValue();
				ret.values[1] = difference;
				return ret;
			}
		}
		

		return ret;
		
	}

	// Uses trackers on a sorted array to find the target value
	static Pair SLMP(int[] array, int target) {
		Pair ret = new Pair();

		// Initialize trackers
		int i = 0, j = array.length - 1;

		while (i < j || i < array.length) {
			if (array[i] + array[j] < target && i < array.length) i++;

			else if (array[i] + array[j] > target) j--;

			else {
				ret.values[0] = array[i];
				ret.values[1] = array[j];
				i++;
				j--;
				return ret;
			}
		}
		return ret;

	}

	// Checks if the array is sorted, if it is it uses a version of SLMP
	public static Pair getCandidatePair(int array[], int isSorted, int target) {
		Pair ret;
		if (isSorted == 1) {
			ret = SLMP(array, target);
		} else {
			HashSet<Integer> set = new HashSet<>();
			for (int ele : array) {
				set.add(ele);
			}
			ret = hashSearch(set, target);
		}
		return ret;
	}
	

	// Driver
	public static void main(String []args) throws FileNotFoundException{
		File file = new File("in.txt");
		Scanner scanner = new Scanner(file);

		if (scanner.hasNextLine()) {
			// Quantity of test cases
			int testCases = scanner.nextInt();
			// Sorted: 0 is sorted, 1 is not
			int isSorted = -1;
			// Array size
			int n = 0;
			// Target search value
			int target = -1;
			int[] array;
			Pair pair;


			// Read in data
			for (int i = 0; i < testCases; i++) {

				isSorted = scanner.nextInt();
				n = scanner.nextInt();
				array = new int[n];
			       	for (int j = 0; j < n; j++) {
					array[j] = scanner.nextInt();	

				}
			       	target = scanner.nextInt();

				// Attempt to search for pairs
				pair = getCandidatePair(array, isSorted, target);

				// Output results
				if (pair.values[0] == 0 && pair.values[1] == 0) {
					System.out.println("Test case#" + (i+1) + ": Target " + target + " is NOT achievable.");
				}
				else {
					System.out.println("Test case#" + (i+1)  + ": Target " + target + " achievable by " + pair.values[0] + " and " + pair.values[1]);
				}


			}	
		}
	}
}
