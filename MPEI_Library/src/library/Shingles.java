package library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Shingles {
	ArrayList<Book> arr;
	int k;
	ArrayList<ArrayList<Boolean>> matrix = new ArrayList<ArrayList<Boolean>>();
	ArrayList<String> shingles = new ArrayList<String>();
	public Shingles(ArrayList<Book> arr, int k, int opt) {
		this.arr = arr;
		this.k = k;
		this.createMatrix(opt);
	}
	
	public ArrayList<ArrayList<Boolean>> getMatrix() {
		return matrix;
	}
	public ArrayList<String> getShingles() {
		return shingles;
	}

	private void createMatrix(int opt) {
		Iterator obj = arr.iterator();
		ArrayList<Boolean> doc = new ArrayList<Boolean>();
		Collections.fill(doc, Boolean.FALSE);
		if (opt == 1) {
			while (obj.hasNext()) {
				String path = ((Book) obj.next()).getPath();
				try {
					BufferedReader bookfile = new BufferedReader(new FileReader(path));
					int ch = 0;
					while (ch != 1) {
						String shingle = "";
						int count = 0;
						while (((ch = bookfile.read()) != -1) && count < k) {
							shingle += (char) ch;
							count++;
						}
						if (!shingles.contains(shingle)) {
							shingles.add(shingle);
						}
						int index = shingles.indexOf(shingle);
						doc.add(index, Boolean.TRUE);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				matrix.add(doc);
			}
		} else {
			while (obj.hasNext()) {
				String line = null;
				if (opt == 2) {
					line = ((Book) obj.next()).getTitle();
				} else if (opt == 3) {
					line = ((Book) obj.next()).getAuthor();
				}
				Boolean loop = true;
				while (loop) {
					String shingle = "";
					int count = 0;
					int mi = k;
					if (line.length() < k) {
						mi = line.length();
						loop = false;
					}
					for (int i = 0; i < mi; i++) {
						shingle += line.charAt(i);
					}
					if (!shingles.contains(shingle)) {
						shingles.add(shingle);
					}
					int index = shingles.indexOf(shingle);
					doc.add(index, Boolean.TRUE);
					line = line.substring(mi);
				}
			}
		}
	}
}
