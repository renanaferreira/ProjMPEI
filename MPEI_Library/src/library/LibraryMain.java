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
		// Variaveis mais usadas
		ArrayList<Book> bookarr = new ArrayList<>();
		BloomFilter books = null;
		BloomCounter contador = null;
		Scanner in = new Scanner(System.in);
		int opt;
		while(true) {
			// Primeiro Menu
			System.out.print("(1) Criar os livros e o bloom filter.\n"
					+ "(2) Criar e guardar o minhash dos livros (o ficheiro dos livros é necessário).\n"
					+ "(3) Fazer testes.\n"
					+ "(0) Terminar testes.\n"
					+ "? ");
			opt = in.nextInt();
			if (opt == 1) {
				// calcular o numero de funcoes hash optimas para este caso particular
				int nfilters = (int) (0.693*10000/2000);
				// criacao de variaveis temporarias por cause de conflitos com o Files.walk
				ArrayList<Book> tbookarr = new ArrayList<>();
				BloomFilter tbooks = new BloomFilter(10000,nfilters);
				BloomCounter filtro = new BloomCounter(10000,nfilters);
				// completacao do array de livros e bloom filter
				try (Stream<Path> paths = Files.walk(Paths.get("PBooks"))) {
					paths
					.filter(Files::isRegularFile)
					.forEach(path -> {
						try {
							Book b = new Book(path.toString(),1);
							tbookarr.add(b);
							tbooks.insert(b);
							filtro.insert(b);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// associar array e bloom filter calculados as variaveis usadas
				bookarr = tbookarr;
				books = tbooks;
				contador = filtro;
				try {
					// gravacao do array e bloom filter para serem usados sem calculacao no futuro
					ObjectOutputStream warray =new ObjectOutputStream(new FileOutputStream("bookArray.ser"));
					warray.writeObject(bookarr);
					warray.close();
					ObjectOutputStream wbloom =new ObjectOutputStream(new FileOutputStream("bloomFilter.ser"));
					wbloom.writeObject(books);
					wbloom.close();
					ObjectOutputStream wcounter =new ObjectOutputStream(new FileOutputStream("bloomCounter.ser"));
					wcounter.writeObject(contador);
					wcounter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (opt == 2) {
				try {
					// como esta opcao modifica o array, e necessario carrega-lo
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
				// submenu da opcao 2
				System.out.print("(1) Fazer minhash do conteúdo dos livros(letter-by-letter) !Não recomendado!.\n"
							+ "(2) Fazer minhash dos títulos(letter-by-letter) .\n"
							+ "(3) Fazer minhash do conteúdo dos livros(word-by-word).\n"
							+ "(4) Fazer minhash dos títulos(word-by-word).\n"
							+ "? ");
					int opt2 = in.nextInt();
					int counter = 0;
					switch (opt2) {
					case 1:
					case 3:
						// criacao do minhash para o conteudo do livro
						Iterator<Book> it1 = bookarr.iterator();
						MinHash forfiles = new MinHash(10, 100, opt2);
						while (it1.hasNext()) {
							it1.next().makeMinHash(forfiles);
							System.out.println(++counter);
						}
						break;
					case 2:
					case 4:
						// criacao do minhash para o titulo do livro
						Iterator<Book> it2 = bookarr.iterator();
						MinHash forstrings = new MinHash(2, 100, opt2);
						while (it2.hasNext()) {
							it2.next().makeMinHash(forstrings);
							System.out.println(++counter);
						}
						break;
					}
					try {
						// guardar as mudancas feitas ao array
						ObjectOutputStream warray =new ObjectOutputStream(new FileOutputStream("bookArray.ser"));
						warray.writeObject(bookarr);
						warray.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			} else if (opt == 3) {
				try {
					// opcao de testes utiliza as variaveis criadas e guardadas antes
					ObjectInputStream rarray = new ObjectInputStream(new FileInputStream("bookArray.ser"));
					bookarr = (ArrayList<Book>) rarray.readObject();
					rarray.close();
					ObjectInputStream rbloom = new ObjectInputStream(new FileInputStream("bloomFilter.ser"));
					books = (BloomFilter)rbloom.readObject();
					rbloom.close();    
					ObjectInputStream rcounter = new ObjectInputStream(new FileInputStream("bloomCounter.ser"));
					contador = (BloomCounter) rcounter.readObject();
					rcounter.close(); 
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// submenu da opcao 3
				System.out.print("(1) Ver Similaridade.\n"
						+ "(2) Ver existencia de livro no arquivo.\n"
						+ "(3) Marcar livro como requisitado.\n"
						+ "(4) Desmarcar livro como requisitado.\n"
						+ "? ");
				int opt3 = in.nextInt();
				switch (opt3) {
				case 1:
					System.out.print("(1) Pesquisar títulos(letter-by-letter).\n"
							+ "(2) Pesquisar conteúdo de livros(letter-by-letter).\n"
							+ "(3) Pesquisar títulos(word-by-word).\n"
							+ "(4) Pesquisar conteúdo de livros(word-by-word)\n"
							+ "? ");
					int opt31 = in.nextInt();
					if (opt31 == 1 || opt31 == 3) {
						// pesquisa atraves de minhash de titulos
						System.out.print("Insira título: ");
						String sctitle = in.next()+in.nextLine();
						Similarity Jacard = new Similarity(sctitle,new MinHash(2, 100, opt31+1),bookarr);
						ArrayList<Book> matches = new ArrayList<Book>();
						ArrayList<Double> matchper = new ArrayList<Double>();
						double[] percentages = Jacard.getPercentages();
						for (int i = 0; i < percentages.length; i++) {
							if (percentages[i] > 0.1) {
								matches.add(bookarr.get(i));
								matchper.add(percentages[i]);
							}
						}
						System.out.print("Encontramos "+matches.size()+" possíveis. Quer vê-los?\n(Y/N): ");
						if (in.next().toUpperCase().equals("Y")) {
							for (int i = 0; i < matches.size(); i++) {
								System.out.println(matchper.get(i)+"-"+matches.get(i));
							}
						}
					} else if (opt31 == 2 || opt31 == 4) {
						// pesquisa atraves do minhash do conteudo do livro
						// possibilidade de usar um ficheiro criado separadamente
						System.out.print("Usar ficheiro teste? (Y/N): ");
						Similarity Jacard;
						if (in.next().toUpperCase().equals("Y")) {
							Jacard = new Similarity("testBook.txt",new MinHash(10, 100, opt31-1),bookarr);
						} else {
							System.out.print("Insira path: ");
							String scbook = in.next();
							System.out.println(scbook);
							Jacard = new Similarity(scbook,new MinHash(10, 100, opt31-1),bookarr);
						}
						ArrayList<Book> matches = new ArrayList<Book>();
						ArrayList<Double> matchper = new ArrayList<Double>();
						double[] percentages = Jacard.getPercentages();
						for (int i = 0; i < percentages.length; i++) {
							if (percentages[i] > 0.4) {
								matches.add(bookarr.get(i));
								matchper.add(percentages[i]);
							}
						}
						System.out.print("Encontramos "+matches.size()+" possíveis. Quer vê-los?\n(Y/N): ");
						if (in.next().toUpperCase().equals("Y")) {
							for (int i = 0; i < matches.size(); i++) {
								System.out.println(matchper.get(i)+"-"+matches.get(i));
							}
						}
					}
					break;
				case 2:
					System.out.print("(1) Existencia do título. \n"
							+ "(2) Livro esta disponivel. \n"
							+ "? ");
					int opt32 = in.nextInt();
					if(opt32 == 1) {
						System.out.print("Qual o título da obra? ");
						String str = in.next()+in.nextLine();
						if(books.contains(new Book(str,2))) {
							System.out.println("O livro tem uma grande possibilidade de estar no acervo");
						}else {
							System.out.println("O livro não está no acervo");
						}
					} else if(opt32 == 2) {
						System.out.print("Qual o título da obra? ");
						String str = in.next()+in.nextLine();
						int quant = contador.contains(new Book(str,2));
							if(quant > 0) {
								System.out.println("O livro está no máximo " + quant + " vezes no acervo");
							}else {
								System.out.println("O titulo que você inseriu não se encontra no acervo");
							}
						}
					break;
				case 3:
					System.out.print("Qual o título da obra? ");
					String stri = in.next()+in.nextLine();
					contador.insert(new Book(stri,2));
					try {
						ObjectOutputStream wcounter = new ObjectOutputStream(new FileOutputStream("bloomCounter.ser"));
						wcounter.writeObject(contador);
						wcounter.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					System.out.print("Qual o título da obra? ");
					String strr = in.next()+in.nextLine();
					contador.remove(new Book(strr,2));
					try {
						ObjectOutputStream wcounter = new ObjectOutputStream(new FileOutputStream("bloomCounter.ser"));
						wcounter.writeObject(contador);
						wcounter.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
					}
				} else if(opt == 0) {
				in.close();
				break;
			}
			
		}
		
	}

}
