import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		Random rand = new Random();
		int n = 10000;
		int[] arr = new int[n];

		for(int i = 0; i < n; i++) {
			arr[i] = rand.nextInt(10000) + 1;
		}

		File file = new File("array.txt");

		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(arr);
			oos.close();
			fos.close();
		}
		catch (IOException e) {
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
	}
}