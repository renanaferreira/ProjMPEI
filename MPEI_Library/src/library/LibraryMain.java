package library;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Stream;

public class LibraryMain {

	public static void main(String[] args) {
		ArrayList<Book> bookarr = new ArrayList<>();
		BloomFilter books = null;
		System.out.print("(1) Criar os livros e o bloom filter.\n"
				+ "(2) Criar e guardar o minhash dos livros (o ficheiro dos livros é necessário).\n"
				+ "(3) Fazer testes.\n"
				+ "? ");
		Scanner in = new Scanner(System.in);
		int opt = in.nextInt();
		if (opt == 1) {
			int nfilters = (int) (0.693*10000/2000);
			ArrayList<Book> tbookarr = new ArrayList<>();
			BloomFilter tbooks = new BloomFilter(10000,nfilters);
			try (Stream<Path> paths = Files.walk(Paths.get("PBooks"))) {
				paths
				.filter(Files::isRegularFile)
				.forEach(path -> {
					try {
						Book b = new Book(path.toString());
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
				ObjectInputStream rarray = new ObjectInputStream(new FileInputStream("bookArray.ser"));
				bookarr = (ArrayList<Book>) rarray.readObject();
				rarray.close();    
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print("(1) Fazer minhash do conteúdo dos livros.\n"
						+ "(2) Fazer minhash dos títulos.\n"
						+ "? ");
				int opt2 = in.nextInt();
				int counter = 0;
				switch (opt2) {
				case 1:
					Iterator<Book> it1 = bookarr.iterator();
					MinHash forfiles = new MinHash(10, 100, opt2);
					while (it1.hasNext()) {
						it1.next().makeMinHash(forfiles);
						System.out.println(++counter);
					}
					break;
				case 2:
					Iterator<Book> it2 = bookarr.iterator();
					MinHash forstrings = new MinHash(10, 100, opt2);
					while (it2.hasNext()) {
						it2.next().makeMinHash(forstrings);
						System.out.println(++counter);
					}
					break;
				}
				try {
					ObjectOutputStream warray =new ObjectOutputStream(new FileOutputStream("bookArray.ser"));
					warray.writeObject(bookarr);
					warray.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		} else if (opt == 3) {
			try {
				ObjectInputStream rarray = new ObjectInputStream(new FileInputStream("bookArray.ser"));
				bookarr = (ArrayList<Book>) rarray.readObject();
				rarray.close();
				ObjectInputStream rbloom = new ObjectInputStream(new FileInputStream("bloomFilter.ser"));
				books = (BloomFilter)rbloom.readObject();
				rbloom.close();    
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(bookarr.get(0).getAssT()[2]);
		}
		in.close();
	}

}
