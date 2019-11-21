package library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		String author = book.getAuthor();
		String title = book.getTitle();
	}
	private int hashFunction(String nome, String autor, int k) {
		
		
		
		
	}
}
