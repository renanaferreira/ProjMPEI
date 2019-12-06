package library;

import java.io.Serializable;

public class BloomFilter implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean[] array;
	private int max_size;
	private int nfunc;
	public BloomFilter(int size, int k) {
		max_size = size;
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