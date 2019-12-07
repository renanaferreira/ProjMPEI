package library;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;


public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private String author;
	private String path;
	private boolean available = true;
	private int[] assbook;
	private int[] asstitle;

	public Book(String str, int opt) {
		if(opt == 1) {
			this.path = str;
			setAttributes(str);
		} else {
			this.title = str;
		}
	}
	private void setAttributes(String path){
		try {
			BufferedReader bookfile = new BufferedReader(new FileReader(path));
			String line;
			while ((line = bookfile.readLine()) != null) {
				if (line.startsWith("Title: ")) {
					title = line.substring(7);
					line = bookfile.readLine();
					if (!line.isBlank()) {
						title += line.substring(4);
					}
				}
				if (line.startsWith("Author: ")) {
					author = line.substring(8);
					break;
				}
			}
			bookfile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getPath() {
		return path;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int[] getAssB() {
		return assbook;
	}
	public int[] getAssT() {
		return asstitle;
	}
	public void makeMinHash(MinHash function) {
		// possui MinHash como argumento para possuir consistencia de utilizar o mesmo caso
		if (function.getOpt() == 1 || function.getOpt() == 3) {
			assbook = function.createSignatures(path);
		}
		if (function.getOpt() == 2 || function.getOpt() == 4) {
			asstitle = function.createSignatures(title);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	public int hashCode(int k) {
		if (k > 0) {
			final int prime = 31;
			int result = 1;
			for (int i = 1; i <= k; i++) {
				result = i * prime * result + ((title == null) ? 0 : title.hashCode());
			}
			return result;
		} else {
			return this.hashCode();
		}
	}
	@Override
	public String toString() {
		return "[title=" + title + ", author=" + author + ", path=" + path + ", available=" + available + "]";
	}
	
}