package library;

//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;

public class BloomFilter {
	//private List<Boolean> array = new ArrayList<>();
	private boolean[] array;
	private int max_size;
	public BloomFilter(int size) {
		max_size = size;
		//array = Arrays.asList(new Boolean[size]);
		array = new boolean[size];
	}
	public void insert(Book book) {
		for (int k = 1; k < 4; k++) {
			int hash = book.hashCode(k);
			int index = hash%max_size;
			array[index] = true;
		}
	}
	public boolean contains(Book book) {
		for (int k = 1; k < 4; k++) {
			int hash = book.hashCode(k);
			int index = hash%max_size;
			if (array[index] = false) {
				return false;
			}
		}
		return true;
	}
}
