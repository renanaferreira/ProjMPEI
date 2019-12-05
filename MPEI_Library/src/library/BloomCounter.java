package library;

import java.util.Collections;

public class BloomCounter {
	
	private int[] array;
	private int max_size;
	private static int max_element = 255;
	public BloomCounter(int size) {
		max_size = size;
		//array = Arrays.asList(new Boolean[size]);
		array = new int[size];
		for (int i : array) {
			array[i] = 0;
		}
	}
	public void insert(Book book) throws Exception {
		for (int k = 1; k < 4; k++) {
			int index = (book.hashCode(k) & 0x7fffffff)%max_size;
			if(array[index] + 1 > 256) throw new Exception("O Filtro excedeu sua capacidade");
			array[index] = array[index] + 1;
		}
	}
	public int contains(Book book) {
		int min = (book.hashCode(1) & 0x7fffffff)%max_size;
		for (int k = 2; k < 4; k++) {
			int hash = (book.hashCode(k) & 0x7fffffff)%max_size;
			if (array[hash] == 0) {
				return -1;
			}
			if(hash < min) min = hash;
		}
		return min;
	}
	public void renitiate() {
		this.array = new int[this.max_size];
	}
	
	public void remover(Book book) throws Exception {
		for (int k = 1; k < 4; k++) {
			int hash = (book.hashCode(k) & 0x7fffffff)%max_size;
			if (array[hash] == 0) {
				throw new Exception("Livro não estava inserido no arquivo");
			}
		}
	}

}
