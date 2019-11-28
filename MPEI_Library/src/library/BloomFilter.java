package library;

//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;

public class BloomFilter {
	//private List<Boolean> array = new ArrayList<>();
	private boolean[] array;
	private int max_size;
	private int nfunc;
	public BloomFilter(int size, int k) {
		max_size = size;
		//array = Arrays.asList(new Boolean[size]);
		array = new boolean[size];
		nfunc = k;
	}
	public void insert(Book book) {
		for (int k = 1; k <= nfunc; k++) {
			int hash = (book.hashCode(k) & 0x7fffffff);
			int index = hash%max_size;
			array[index] = true;
		}
	}
	public boolean contains(Book book) {
		for (int k = 1; k <= nfunc; k++) {
			int hash = (book.hashCode(k) & 0x7fffffff);
			int index = hash%max_size;
			if (!array[index]) {
				return false;
			}
		}
		return true;
	}
}