package library;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class LibraryMain {

	public static void main(String[] args) {
		ArrayList<Book> bookarr = new ArrayList<>();
		BloomFilter books = null;
		System.out.println("(1) Criar e ler os livros e o bloom filter.");
		System.out.println("(2) Ler os livros e o bloom filter já guardado.");
		System.out.print("?");
		Scanner in = new Scanner(System.in);
		int opt = in.nextInt();
		if (opt == 1) {
			int nfilters = (int) (0.693*20000/8789);
			ArrayList<Book> tbookarr = new ArrayList<>();
			BloomFilter tbooks = new BloomFilter(20000,nfilters);
			try (Stream<Path> paths = Files.walk(Paths.get("PBooks"))) {
				paths
				.filter(Files::isRegularFile)
				.forEach(path -> {
					try {
						String strpath = path.toString();
						BufferedReader bookfile = new BufferedReader(new FileReader(strpath));
						String line;
						String title = null;
						String author = null;
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
						Book b = new Book(title,author,strpath);
						tbookarr.add(b);
						tbooks.insert(b);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bookarr = tbookarr;
			books = tbooks;
			try {
				ObjectOutputStream warray =new ObjectOutputStream(new FileOutputStream("bookArray.ser"));
				warray.writeObject(bookarr);
				warray.close();
				ObjectOutputStream wbloom =new ObjectOutputStream(new FileOutputStream("bloomFilter.ser"));
				wbloom.writeObject(books);
				wbloom.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (opt == 2) {
			try {
				ObjectInputStream rarray=new ObjectInputStream(new FileInputStream("bookArray.ser"));
				bookarr = (ArrayList<Book>)rarray.readObject();
				rarray.close();
				ObjectInputStream rbloom=new ObjectInputStream(new FileInputStream("bloomFilter.ser"));
				books = (BloomFilter)rbloom.readObject();
				rbloom.close();        
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		Shingles sobj = new Shingles(bookarr, 10, 1);
//		ArrayList<String> shingle = sobj.getShingles();
		
	}

}
