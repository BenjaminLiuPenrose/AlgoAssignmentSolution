import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args) 
	{
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		String s;
		while (!StdIn.isEmpty()) {
			s = StdIn.readString();
			rq.enqueue(s);
		}

		Iterator<String> iterator = rq.iterator();
		for (int i = 0; i < k; i++) {
			String item = iterator.next();
			StdOut.println(item);
		}
	}
}
