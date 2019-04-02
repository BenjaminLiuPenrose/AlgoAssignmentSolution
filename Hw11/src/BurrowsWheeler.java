import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	private static final int R = 256;
	
	public static void transform() 
	{
		String s = BinaryStdIn.readString();
		CircularSuffixArray csa = new CircularSuffixArray(s);
		int first = 0;
		while (first < csa.length() && csa.index(first) != 0) 
		{
			first ++;
		}
		BinaryStdOut.write(first);
		for (int i = 0; i < csa.length(); i++) 
		{
			BinaryStdOut.write(s.charAt( (csa.index(i) + s.length() - 1) % s.length() ));
		}
		BinaryStdOut.close();
	}
	public static void inverseTransform() 
	{
		int first = BinaryStdIn.readInt();
		String s = BinaryStdIn.readString();
		int n = s.length();
		int[] count = new int[R + 1], next = new int[n];
		for(int i = 0; i < n; i++) 
		{
			count[s.charAt(i) + 1]++;
		}
		for(int i = 1; i < R + 1; i++) 
		{
			count[i] += count[i - 1];
		}
		for(int i = 0; i < n; i++) 
		{
			next[count[s.charAt(i)]++] = i;
		}
		for(int i = next[first], c= 0; c < n; i = next[i], c++) 
		{
			BinaryStdOut.write(s.charAt(i));
		}
		BinaryStdOut.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length != 1) { throw new IllegalArgumentException("Expected + or -\n"); }
		else if(args[0].equals("+")) { inverseTransform(); }
		else if(args[0].equals("-")) { transform(); }
		else { throw new IllegalArgumentException("Unknown argument: " + args[0] + "\n"); }
	}

}
