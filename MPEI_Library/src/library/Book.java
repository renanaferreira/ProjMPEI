package library;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {
	private String title;
	private String author;
	private String path;
	private boolean available = true;
	public Book(String title, String author, String path) {
		this.title = title;
		this.author = author;
		this.path = path;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", path=" + path + ", available=" + available + "]";
	}
}
