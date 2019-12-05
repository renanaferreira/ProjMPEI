package library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

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

	public int[] createSignatures(String input) {
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
					int nsh = (int) line.length()/k;
					for (int j = 0; j+k <= nsh*k; j+=k) {
						shingle = line.substring(j,j+k);
						for (int i = 0; i < nhfunc; i++) {
							shingle += i;
							int hash = shingle.hashCode();
							if (hash < output[i]) {
								output[i] = hash;
							}
						}
					}
					left = line.substring(nsh*k);
				}
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
		return output;
	}
}
