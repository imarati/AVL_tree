package ru.itis.marat;

import ru.itis.marat.avl.AVLTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * This class represents the entry point for the AVL Tree program. It reads an array of integers from a file, builds an AVL tree from it,
 * and performs operations such as insertion, search, and deletion on the tree.
 */
public class Main {

	/**
	 * The main method of the program. It reads an array of integers from a file, builds an AVL tree from it, and performs operations such as
	 * insertion, search, and deletion on the tree. It also prints out the average time and number of iterations taken for each operation.
	 * @param args The command line arguments. Currently unused in the program.
	 * @throws Exception If there is an error reading the array from the file or performing an operation on the tree.
	 */
	public static void main(String[] args) throws Exception {
		File file = new File("array.txt");
		int[] arr = getArrayFromFile(file);
		int n = arr.length;

		AVLTree avl = new AVLTree();

		System.out.println("Inserting");
		for (int i = 0; i < n; i++) {
			avl.insert(arr[i]);
		}

		System.out.println("Avg inserting time " + avl.getAvgInsertTime());
		System.out.println("Avg inserting iteration " + avl.getAvgInsertIteration());
		System.out.println();

		int[] arrForSearch = getACertainNumberOfElements(arr, 100);
		int ns = arrForSearch.length;

		System.out.println("Searching");
		for (int i = 0; i < ns; i++) {
			avl.find(arrForSearch[i]);
		}

		System.out.println("Avg searching time " + avl.getAvgSearchTime());
		System.out.println("Avg searching iteration " + avl.getAvgSearchIteration());
		System.out.println();

		int[] arrForDeleting = getACertainNumberOfElements(arr, 1000);
		int nd = arrForDeleting.length;

		System.out.println("Deleting");
		for (int i = 0; i < nd; i++) {
			avl.delete(arrForDeleting[i]);
		}

		System.out.println("Avg deleting time " + avl.getAvgDeleteTime());
		System.out.println("Avg deleting iteration " + avl.getAvgDeleteIteration());
		System.out.println();
	}

	/**
	 * Reads an array of integers from a file.
	 * @param file The file to read the array from.
	 * @return An array of integers read from the file.
	 * @throws Exception If there is an error reading the array from the file.
	 */
	public static int[] getArrayFromFile(File file) throws Exception {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			int[] arr = (int[]) ois.readObject();
			ois.close();
			fis.close();
			return arr;
		}
		catch (IOException e) {
			System.out.println(Arrays.toString(e.getStackTrace()));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		throw new Exception("Failed to create array!");
	}

	/**
	 * Returns an array containing a certain number of elements randomly selected from an input array.
	 * @param arr The input array to select elements from.
	 * @param num The number of elements to select.
	 * @return An array containing a certain number of elements randomly selected from the input array.
	 */
	public static int[] getACertainNumberOfElements(int[] arr, int num) {
		Random rand = new Random();
		int[] res = new int[num];

		for (int i = 0; i < num; i++) {
			res[i] = arr[rand.nextInt(arr.length)];
		}

		return res;
	}
}