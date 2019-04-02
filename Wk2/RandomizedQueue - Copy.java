/*
Name: Beier (Benjamin) Liu
Date: 5/21/2018

Remark:
*/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Iterator;

/*===================================================================================================
File content:
Class Randomized Queue
===================================================================================================*/

public class RandomizedQueue<Item> implements Iterable<Item>{

	private Item[] rq;
	private int n=0;

	// construct an empty randomized queue
	public RandomizedQueue() {
		rq=(Item[]) new Object[2];
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return n==0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return n;
	}

	private void resize(int maxi) {
		Item[] temp=(Item[]) new Object[maxi];
		for (int i=0; i<n; i++) {
			temp[i]=rq[i];
		}
		rp=temp
	}

	// add the item
	public void enqueue(Item item) {
		if (item==null) {
			throw new java.lang.NullPointerException();
		}
		if (n==rq.length) {
			resize(2*rq.length);
		}
		rq[n++]=item
	}

	// remove and return a random item
	public Item dequeue() {
		if (isEmpty()) {
			throw new java.lang.NullPointerException();
		}
		int index= (int) (Math.random()*n);
		Item item=rq[index];
		if (index!=n-1) {
			rq[index]=rq[n-1];
		}
		rq[n-1]=null;
		n--;
		if(N>0 && N==rq.length/4) {
			resize(rq.length/2);
		}
		return item;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementEeception();
		}
		int index=(int) (Math.random()*n);
		Item item=rq[index];
		return item;
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RQIterator();
	}

	// unit testing (optional)
	public static void main(String[] args) {}

}

