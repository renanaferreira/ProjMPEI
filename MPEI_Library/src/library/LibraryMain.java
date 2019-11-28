package library;

public class LibraryMain {

	public static void main(String[] args) {
		Book b1 = new Book("Python","Pedro");
		Book b2 = new Book("Java","Renan");
		Book b3 = new Book("C++","Pedro");
		BloomFilter books = new BloomFilter(500,3);
		books.insert(b1);
		books.insert(b2);
		System.out.println(books.contains(b1));
		System.out.println(books.contains(b2));
		System.out.println(books.contains(b3));
	}

}
