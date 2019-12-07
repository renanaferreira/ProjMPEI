package library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class MinHash {
	private int k;
	private int nhfunc;
	private int opt;
	public MinHash(int k, int nhfunc, int opt) {
		this.k = k;
		this.nhfunc = nhfunc;
		this.opt = opt;
	}

	public int getOpt() {
		return opt;
	}
	public int getNHfunc() {
		return nhfunc;
	}
	public int[] createSignatures(String input) {
		// possui opcao de ler string ou ficheiro
		int[] output = new int[nhfunc];
		Arrays.fill(output, Integer.MAX_VALUE);
		if (opt == 1) {
			try {
				BufferedReader bookfile = new BufferedReader(new FileReader(input));
				String line = "";
				String left = "";
				String shingle = "";
				while ((line = bookfile.readLine()) != null) {
					line = left+line;
					for (int j = 0; j <= line.length()-k; j++) {
						shingle = line.substring(j,j+k);
						for (int i = 0; i < nhfunc; i++) {
							shingle += i;
							int hash = shingle.hashCode();
							if (hash < output[i]) {
								output[i] = hash;
							}
						}
					}
					left = shingle.substring(1);
				}
				bookfile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (opt == 2) {
			for (int j = 0; j <= input.length()-k; j++) {
				String shingle = input.substring(j,j+k);
				for (int i = 0; i < nhfunc; i++) {
					shingle += i;
					int hash = shingle.hashCode();
					if (hash < output[i]) {
						output[i] = hash;
					}
				}
			}
		}
		if (opt == 3) {
			try {
				BufferedReader bookfile = new BufferedReader(new FileReader(input));
				String[] shingle = new String[k];
				String hshingle = "";
				int ck = 0;
				String line;
				while ((line = bookfile.readLine()) != null) {
					String[] words = line.trim().split("\\s+");
					for (String word : words) {
						if (ck < k-1) {
							shingle[ck++] = word.trim();
						} else {
							shingle[ck] = word.trim();
							hshingle = String.join(" ",shingle);
							for (int i = 0; i < nhfunc; i++) {
								hshingle += i;
								int hash = hshingle.hashCode();
								if (hash < output[i]) {
									output[i] = hash;
								}
							}
							for (int i = 0; i < k-1; i++) {
								shingle[i] = shingle[i+1];
							}

						}
					}
				}
				hshingle = String.join(" ",shingle);
				for (int i = 0; i < nhfunc; i++) {
					hshingle += i;
					int hash = hshingle.hashCode();
					if (hash < output[i]) {
						output[i] = hash;
					}
				}
				bookfile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (opt == 4) {
			String[] shingle = new String[k];
			String hshingle = "";
			int ck = 0;
			String[] words = input.trim().split("\\s+");
			for (String word : words) {
				if (ck < k-1) {
					shingle[ck++] = word.trim();
				} else {
					shingle[ck] = word.trim();
					hshingle = String.join(" ",shingle);
					for (int i = 0; i < nhfunc; i++) {
						hshingle += i;
						int hash = hshingle.hashCode();
						if (hash < output[i]) {
							output[i] = hash;
						}
					}
					for (int i = 0; i < k-1; i++) {
						shingle[i] = shingle[i+1];
					}

				}
			}
			hshingle = String.join(" ",shingle);
			for (int i = 0; i < nhfunc; i++) {
				hshingle += i;
				int hash = hshingle.hashCode();
				if (hash < output[i]) {
					output[i] = hash;
				}
			}
		}
		return output;
	}
}
