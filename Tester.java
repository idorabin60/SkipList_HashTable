import java.util.Scanner;

public class Tester {
	public static void main(String args[]) {
		System.out.println("Insert number of task to test, or 2.0 and so on to test all tasks");
		Scanner scanner = new Scanner(System.in);
		String taskNumber = scanner.nextLine();

		if (taskNumber.equals("2.0")) {
			task2_1();
			task2_2();
			task2_3();
			task2_4();
			task2_5();
		} else if (taskNumber.equals("2.1")) {
			task2_1();
		} else if (taskNumber.equals("2.2")) {
			task2_2();
		} else if (taskNumber.equals("2.3")) {
			task2_3();
		} else if (taskNumber.equals("2.4")) {
			task2_4();
		} else if (taskNumber.equals("2.5")) {
			task2_5();
		} else if (taskNumber.equals("3.1")) {
			task3_1();
		} else if (taskNumber.equals("3.2")) {
			task3_2();
		} else if (taskNumber.equals("3.3")) {
			task3_3();
		} else if (taskNumber.equals("3.4")) {
			task3_4();
		} else if (taskNumber.equals("3.5")) {
			task3_5();
		} else if (taskNumber.equals("3.6")) {
			task3_6();
		} else if (taskNumber.equals("3.7")) {
			task3_7();
		} else if (taskNumber.equals("3.8")) {
			task3_8();
		}

	}

	public static void task2_1() {
		String testText = "Testing task 2.1: \n";
		IndexableSkipList list = new IndexableSkipList(0.5);
		for (int i = 0; i < 10; i = i + 2) {
			list.insert(i);
		}
		for (int i = 1; i < 10; i = i + 2) {
			list.insert(i);
		}
		testText += "Test 1 - Find element, your result:" + list.find(4).toString() + ", Should be: [4] \n";
		testText += "Test 2 - Find minimum, your result:" + list.find(0).toString() + ", Should be: [0] \n";
		testText += "Test 3 - Find maximum, your result:" + list.find(9).toString() + ", Should be: [9] \n";
		testText += "Test 4 - Find out of bounds below, your result:" + list.find(-5).toString()
				+ ", Should be: [-2147483648] \n";
		testText += "Test 5 - Find out of bounds above, your result:" + list.find(20).toString()
				+ ", Should be: [9] \n";
		System.out.println(testText);
	}

	public static void task2_2() {
		String testText = "Testing task 2.2: \nTest: Your results: ";
		IndexableSkipList list = new IndexableSkipList(0.5);
		for (int i = 0; i < 9; i++) {
			testText += list.generateHeight() + ", ";
		}
		System.out.println(testText + list.generateHeight()
				+ ". \nThey should all be reasonable - usually from 0 to 5. A lot of high numbers (or ones below zero) indicate a problem. \n");

	}

	public static void task2_3() {
		String testText = "Testing task 2.3:\n";
		SkipListUtils tools = new SkipListUtils();
		testText += "Test 1 - Probability 1/2, your result: " + tools.calculateExpectedHeight(0.5)
				+ ". should be: 2.0 \n";
		testText += "Test 2 - Probability 1/4, your result: " + tools.calculateExpectedHeight(0.25)
				+ ". should be: 4.0 \n";
		testText += "Test 3 - Probability 1/8, your result: " + tools.calculateExpectedHeight(1.0 / 8)
				+ ". should be: 8.0 \n";
		testText += "Test 4 - Probability 1/16, your result: " + tools.calculateExpectedHeight(1.0 / 16)
				+ ". should be: 16.0 \n";
		System.out.println(testText);
	}

	public static void task2_4() {
		System.out.println("Testing task 2.4: Ten elements:");
		IndexableSkipList list = new IndexableSkipList(0.5);
		for (int i = 0; i < 10; i = i + 2) {
			list.insert(i);
		}
		for (int i = 1; i < 10; i = i + 2) {
			list.insert(i);
		}
		System.out.println("Test 1 - Rank of 5: your result: " + list.rank(5) + ", Should be: 6");
		System.out.println("Test 2 - Rank of max: your result: " + list.rank(9) + ", Should be: 10");
		System.out.println("Test 3 - Rank of min: your result: " + list.rank(0) + ", Should be: 1 ");
		System.out.println("Test 4 - Selecting 3: your result: " + list.select(3) + ", Should be: 2 ");
		System.out.println("Test 5 - Selecting max: your result: " + list.select(10) + ", Should be: 9");
		System.out.println("Test 6 - Selecting min: your result: " + list.select(1) + ", Should be: 0 ");

		System.out.println("\n2.4: Performing Deletion: 7 elements");
		System.out.println("Test 7 - Rank of 5: your result: " + list.rank(5) + ", Should be: 4 ");
		System.out.println("Test 8 - Rank of max: your result: " + list.rank(8) + ", Should be: 7 ");
		System.out.println("Test 9 - Rank of min: your result: " + list.rank(1) + ", Should be: 1 ");
		System.out.println("Test 10 - Selecting 3: your result: " + list.select(3) + ", Should be: 3 ");
		System.out.println("Test 11 - Selecting max: your result: " + list.select(7) + ", Should be: 8 ");
		System.out.println("Test 12 - Selecting min: your result: " + list.select(1) + ", Should be: 1 ");
		for (int i = 0; i < 10; i = i + 1) {
			list.delete(list.find(i));
		}
		for (int i = 0; i < 10; i = i + 1) {
			list.insert(i);
		}
		System.out.println(
				"\n2.4: This Test empties the list and fills it again - yet the Tail and Head nodes do not resize.");
		System.out.println("Test 13 - Rank of min: your result: " + list.rank(0) + ", Should be: 1");
		System.out.println("Test 14 - Rank of max: your result: " + list.rank(9) + ", Should be: 10 \n");

	}

	public static void task2_5() {
		SkipListUtils tools = new SkipListUtils();
		boolean[] arr = tools.changedMethodsArray();
		int counter = 0;
		for (int i = 0; i < 9; i++) {
			if (arr[i] == true)
				counter++;
		}
		if (counter > 3)
			System.out.println("Testing task 2.5: Failed - the array has too many cells flagged as true.");
		else
			System.out.println("Testing task 2.5: Passed.");

	}

	public static void task3_1() {
		System.out.println("\nTesting task 3.1: ModularHash");
		ModularHash hashFactory = new ModularHash();
		System.out.println(
				"Testing small k for 2^k table: values should be between 0 and 7, and probably not the same number");
		HashFunctor<Integer> hashFunc = hashFactory.pickHash(3);
		System.out.println("Hash 1 your result: " + hashFunc.hash(10));
		System.out.println("Hash 2 your result: " + hashFunc.hash(11));
		System.out.println("Hash 3 your result: " + hashFunc.hash(12));
		System.out.println("Hash 4 your result: " + hashFunc.hash(13));

		System.out.println(
				"\nTesting for k over 31: values should be between 0 and 1,073,741,824, and probably not the same number");
		hashFunc = hashFactory.pickHash(100);
		System.out.println("Hash 5 your result: " + hashFunc.hash(10));
		System.out.println("Hash 6 your result: " + hashFunc.hash(11));
		System.out.println("Hash 7 your result: " + hashFunc.hash(12));
		System.out.println("Hash 8 your result: " + hashFunc.hash(13));

	}

	public static void task3_2() {
		System.out.println("\nTesting task 3.2: MultiplicativeShiftingHash");
		MultiplicativeShiftingHash hashFactory = new MultiplicativeShiftingHash();
		System.out.println(
				"Testing small k for 2^k table: all values should be between 0 and 7, and probably not the same number");
		HashFunctor<Long> hashFunc = hashFactory.pickHash(3);
		System.out.println("Hash 1 your result: " + hashFunc.hash((long) 10));
		System.out.println("Hash 2 your result: " + hashFunc.hash((long) 11));
		System.out.println("Hash 3 your result: " + hashFunc.hash((long) 12));
		System.out.println("Hash 4 your result: " + hashFunc.hash((long) 13));

		System.out.println(
				"\nTesting for k over 31: all values should be between 0 and 1,073,741,824, and probably not the same number");
		hashFunc = hashFactory.pickHash(100);
		System.out.println("Hash 5 your result: " + hashFunc.hash((long) 10));
		System.out.println("Hash 6 your result: " + hashFunc.hash((long) 11));
		System.out.println("Hash 7 your result: " + hashFunc.hash((long) 12));
		System.out.println("Hash 8 your result: " + hashFunc.hash((long) 13));

	}

	public static void task3_3() {
		System.out.println("\nTesting task 3.3: Chaining HashTable");
		ChainedHashTable<Long, Long> table = new ChainedHashTable(new MultiplicativeShiftingHash(), 1, 2);
		System.out.println("Checking insert, search and search:");
		for (long i = -15; i < 15; i++) {
			table.insert(i, i);
		}
		System.out.println("Table Capacity: " + table.capacity() + ", should be 16");
		System.out.println("Test 1 - your result: " + table.search((long) 5) + ", Should be: 5 ");
		System.out.println("Test 2 - your result: " + table.search((long) -7) + ", Should be: -7 ");
		System.out.println(
				"Test 3 - element not in table, your result: " + table.search((long) 16) + ", Should be: null ");
		System.out.println("\nChecking deletion:");
		int counter = 0;
		for (long i = -15; i < 0; i++) {
			boolean b = table.delete((long) i);
			if (!b)
				counter++;
		}
		System.out.println(
				"Test 4 - element that shouldnt exist, your result: " + table.search((long) -5) + ", Should be: null ");
		System.out.println("Test 5 - your result: " + table.search((long) 1) + ", Should be: 1 ");
		if (counter != 0) {
			System.out.println(
					"There is a problem with your deletion, or possibly the rehash functions.\nNot all the elements were found.");
		}

	}

	public static void task3_4() {
		System.out.println("\nTesting task 3.4: Probing HashTable");
		ProbingHashTable<Long, Long> table = new ProbingHashTable(new MultiplicativeShiftingHash(), 1, 0.5);
		System.out.println("Checking insert, search and search:");
		for (long i = -15; i < 15; i++) {
			table.insert(i, i);
		}
		System.out.println("Table Capacity: " + table.capacity() + ", should be 64");
		System.out.println("Test 1 - your result: " + table.search((long) 5) + ", Should be: 5 ");
		System.out.println("Test 2 - your result: " + table.search((long) -7) + ", Should be: -7 ");
		System.out.println(
				"Test 3 - element not in table, your result: " + table.search((long) 16) + ", Should be: null ");
		System.out.println("\nChecking deletion:");
		int counter = 0;
		for (long i = -15; i < 14; i++) {
			boolean b = table.delete((long) i);
			if (!b)
				counter++;
		}
		System.out.println(
				"Test 4 - element that shouldnt exist, your result: " + table.search((long) -5) + ", Should be: null ");
		System.out.println("Test 5 - your result: " + table.search((long) 14) + ", Should be: 14 ");
		if (counter != 0) {
			System.out.println(
					"There is a problem with your deletion, or possibly the rehash functions.\nNot all the elements were found.");
		}
	}

	public static void task3_5() {
		System.out.println("Testing task 3.5: Probing Insertion Time");
		HashingExperimentUtils exp = new HashingExperimentUtils();
		double[] avgArr = exp.measureInsertionsProbing();
		System.out.println("The numbers are usually between 40 and 200, could be higher");
		System.out.println("Test 1: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + "]");
		avgArr = exp.measureInsertionsProbing();
		System.out.println("Test 2: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + "]");
		avgArr = exp.measureInsertionsProbing();
		System.out.println("Test 3: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + "]");

	}

	public static void task3_6() {

		System.out.println("Testing task 3.6: Probing Search Time");
		HashingExperimentUtils exp = new HashingExperimentUtils();
		double[] avgArr = exp.measureSearchesProbing();
		System.out.println("The numbers are usually above 50, and going up towards the left");
		System.out.println("Test 1: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + "]");
		avgArr = exp.measureSearchesProbing();
		System.out.println("Test 2: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + "]");
		avgArr = exp.measureSearchesProbing();
		System.out.println("Test 3: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + "]");

	}

	public static void task3_7() {

		System.out.println("Testing task 3.7: Chaining Insertion Time");
		HashingExperimentUtils exp = new HashingExperimentUtils();
		double[] avgArr = exp.measureInsertionsChaining();
		System.out.println("The numbers are usually between 40 and 200, could be higher");
		System.out.println("Test 1: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + ", "
				+ avgArr[4] + "]");
		avgArr = exp.measureInsertionsChaining();
		System.out.println("Test 2: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + ", "
				+ avgArr[4] + "]");
		avgArr = exp.measureInsertionsChaining();
		System.out.println("Test 3: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + ", "
				+ avgArr[4] + "]");
	}

	public static void task3_8() {

		System.out.println("Testing task 3.8: Chaining Searching Time");
		HashingExperimentUtils exp = new HashingExperimentUtils();
		double[] avgArr = exp.measureSearchesChaining();
		System.out.println("The numbers are usually above 50, and could be going up towards the left");
		System.out.println("Test 1: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + ", "
				+ avgArr[4] + "]");
		avgArr = exp.measureSearchesChaining();
		System.out.println("Test 2: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + ", "
				+ avgArr[4] + "]");
		avgArr = exp.measureSearchesChaining();
		System.out.println("Test 3: [" + avgArr[0] + ", " + avgArr[1] + ", " + avgArr[2] + ", " + avgArr[3] + ", "
				+ avgArr[4] + "]");
	}

}
