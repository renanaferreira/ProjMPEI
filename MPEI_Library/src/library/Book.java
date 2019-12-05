package library;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Book {
	private String title;
	private String author;
	private String path;
	private boolean available = true;
	private Signature assinatura;
	private static int K = 10;
	private static final Pattern SPACE_REG = Pattern.compile("\\s+");
	 
	
	public Book(String path) throws FileNotFoundException {
		this.path = path;
		setAttributes(path);
		String str = this.getText(path);
		HashSet<String> palavras = this.getShingles(str);
		this.assinatura = new Signature();
		minHash(palavras);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	public int hashCode(int k) {
		if (k > 0) {
			final int prime = 31;
			int result = 1;
			for (int i = 1; i <= k; i++) {
				result = i * prime * result + ((author == null) ? 0 : author.hashCode());
				result = i * prime * result + ((title == null) ? 0 : title.hashCode());
			}
			return result;
		} else {
			return this.hashCode();
		}
	}
	
	
	public int hashCode(int k, String str) {
		if (k > 0) {
			final int prime = 31;
			int result = 1;
			for (int i = 1; i <= k; i++) {
				result = i * prime * result + ((str == null) ? 0 : str.hashCode());
			}
			return result;
		} else {
			return str.hashCode();
		}
	}
	
	public HashSet<String> getShingles(final String string) {
        HashSet<String> shingles = new HashSet<>();

        String string_no_space = SPACE_REG.matcher(string).replaceAll(" ");
        for (int i = 0; i < (string_no_space.length() - Book.K + 1); i++) {
            String shingle = string_no_space.substring(i, i + Book.K);
            shingles.add(shingle);
        }

        return shingles;
    }
	
    private void setAttributes(String path) throws FileNotFoundException {
    	Scanner input = new Scanner(new File(path));
		String tmp;
		while (input.hasNextLine()) {
			tmp = input.nextLine();
			if(tmp.startsWith("Title: ")) {
				this.title = tmp.substring(7);
			}
			if(tmp.startsWith("Author: ")) {
				this.title = tmp.substring(8);
				break;
			}
		}
		input.close();
    }
	
	private String getText(String path) throws FileNotFoundException {
		Scanner input = new Scanner(new File(path));
		String str = "";
		while (input.hasNextLine()) str += input.nextLine();
		input.close();
		return str;
	}
	
	private void minHash(HashSet<String> shingles) {
		Iterator<String> iterador;
		int tam = assinatura.size();
		int min, hash;
		for(int i = 0; i < tam; i++) {
			iterador = shingles.iterator();
			if(iterador.hasNext()) {
				min = (this.hashCode(i,(String) iterador.next()) & 0x7fffffff);
				while(iterador.hasNext()) {
					hash = (this.hashCode(i,(String) iterador.next()) & 0x7fffffff);
					if(hash < min) min = hash;
				}
				this.assinatura.setIndice(i, min);
			}
		}
	}
}
