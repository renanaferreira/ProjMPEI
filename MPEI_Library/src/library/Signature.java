package library;


public class Signature {
	
	public static int K = 100;
	private int[] array;
	
	public Signature() {
		this.array = new int[Signature.K];
	}
	public int[] getArray() {
		return array;
	}
	
	public void setIndice(int i, int valor) {
		this.array[i] = valor;
	}

	public int size() {
		return Signature.K;
	}

}
