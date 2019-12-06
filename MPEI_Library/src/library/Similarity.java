package library;

import java.util.ArrayList;
import java.util.Iterator;

public class Similarity {
	private double[] percentages;
	private ArrayList<Book> bookarr;
	private String input;
	private MinHash function;
	
	public Similarity(String input, MinHash function, ArrayList<Book> bookarr) {
		this.input = input;
		this.function = function;
		this.bookarr = bookarr;
		percentages = new double[bookarr.size()];
		calculateSimilar();
	}

	public double[] getPercentages() {
		return percentages;
	}
	
	private void calculateSimilar() {
		int[] strass = function.createSignatures(input);
		Iterator<Book> it = bookarr.iterator();
		int index = 0;
		// possibilidade de calcular similhanca de titulo ou conteudo
		switch (function.getOpt()) {
		case 1:
			while (it.hasNext()) {
				int[] assbook = it.next().getAssB();
				int intersect = 0;
				for (int i = 0; i < function.getNHfunc(); ++i) {
					if (strass[i] == assbook[i]) {
						intersect++;
					}
				}
				percentages[index++] = (double)intersect/function.getNHfunc();
			}
			break;
		case 2:
			while (it.hasNext()) {
				int[] asstitle = it.next().getAssT();
				int intersect = 0;
				for (int i = 0; i < function.getNHfunc(); ++i) {
					if (strass[i] == asstitle[i]) {
						intersect++;
					}
				}
				percentages[index++] = (double)intersect/function.getNHfunc();
			}
			break;
		}
		
	}
}
