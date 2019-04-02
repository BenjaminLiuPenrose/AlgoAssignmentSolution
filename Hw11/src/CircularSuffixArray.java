import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
	private static final int CUTOFF = 15;
	private int[] index;
	private int n;
	
	
	public CircularSuffixArray(String s) 
	{
		n = s.length();
		index = new int[n];
		for (int i = 0; i < n; i++) 
		{
			index[i] = i;
		}
		sort(s, 0, n-1, 0);
	}
	private void sort(String s, int lo, int hi, int offset) 
	{
		if (hi - lo <= CUTOFF)  
		{
			insertion(s, lo, hi, offset);
		}
		int lt = lo, gt = hi, piv = charAt(s, index[lo], offset), eq = lo + 1;
		while (eq <= gt) 
		{
			int t = charAt(s, index[eq], offset);
			if (t < piv) { exch(lt++, eq++); } 
			else if (t > piv) { exch(eq, gt--); }
			else { eq++; }
		}
		sort(s, lo, lt - 1, offset);
		if (piv >= 0) { sort(s, lt, gt, offset+1); }
		sort(s, gt + 1, hi, offset);
	}
	private void exch(int i, int j) 
	{
		int tmp = index[i];
		index[i] = index[j];
		index[j] = tmp;
	}
	// insertion sort
	private void insertion(String s, int lo, int hi, int offset) 
	{
		for (int i = lo; i <= hi; i++) 
		{
			for (int j = i; j > lo && less(s, j, j-1, offset); j--) 
			{
				exch(j, j-1);
			}
		}
	}
	private boolean less(String s, int i, int j, int offset) 
	{
		int oi = index[i], oj = index[j];
		for (; offset < n; offset ++) 
		{
			int ival = charAt(s, oi, offset), jval = charAt(s, oj, offset);
			if ( ival < jval ) 
			{
				return true;
			} else if ( ival > jval ) {
				return false;
			} else {
				return false;
			}
		}
		return false;
	}
	private char charAt(String s, int suffix, int offset) 
	{
		return s.charAt((suffix + offset) % n);
	}
	public int length() 
	{
		return this.n;
	}
	public int index(int i) 
	{
		return this.index[i];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int SCREEN_WIDTH = 80;
        String s = StdIn.readString();
        int n = s.length();
        int digits = (int) Math.log10(n) + 1;
        String fmt = "%" + (digits == 0 ? 1 : digits) + "d ";
        StdOut.printf("String length: %d\n", n);
        CircularSuffixArray csa = new CircularSuffixArray(s);
        for (int i = 0; i < n; i++) {
            StdOut.printf(fmt, i);
            for (int j = 0; j < (SCREEN_WIDTH - digits - 1) && j < n; j++) {
                char c = s.charAt((j + csa.index(i)) % n);
                if (c == '\n')
                    c = ' ';
                StdOut.print(c);
            }
            StdOut.printf(fmt, csa.index(i));
            StdOut.println();
        }
	}

}
