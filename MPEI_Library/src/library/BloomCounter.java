package library;

import java.io.Serializable;
import java.util.Collections;

public class BloomCounter implements Serializable{
	private static final long serialVersionUID = 1L;
	private int[] array;
	private int max_size;
	private int nfunc;
	public BloomCounter(int size, int k) {
		max_size = size;
		array = new int[size];
		nfunc = k;
		for(int i = 0;i < this.max_size;i++) {
			array[i] = 0;
		}
	}
	public void insert(Book book) {
		for (int k = 1; k <= nfunc; k++) {
			int hash = (book.hashCode(k) & 0x7fffffff);
			int index = hash%max_size;
			array[index] += 1;
		}
	}
	public int contains(Book book) {
		int hash = (book.hashCode(1) & 0x7fffffff);
		int index = hash%max_size;
		if (array[index] == 0) {
			return -1;
		}
		int min = array[index];
		for (int k = 2; k <= nfunc; k++) {
			hash = (book.hashCode(k) & 0x7fffffff);
			index = hash%max_size;
			if (array[index] == 0) {
				return -1;
			}
			if(array[index] < min) min = array[index];
		}
		return min;
	}
	public void remove(Book book) {
		int[] tmp = array;
		for (int k = 1; k <= nfunc; k++) {
			int hash = (book.hashCode(k) & 0x7fffffff);
			int index = hash%max_size;
			if(array[index] == 0) {
				System.out.println("Erro, livro não está contido no filtro");
				this.array = tmp; // garante que se tiver esse erro, o array não tenha sido realmente mudado
				return;
			}
			array[index] -= 1;
		}
	}
}
