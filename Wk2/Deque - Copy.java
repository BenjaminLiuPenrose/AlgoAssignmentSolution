/*
Name: Beier (Benjamin) Liu
Date: 6/24/2018

Remark:
*/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Iterator;

/*===================================================================================================
File content:
Class Deque "deck"
===================================================================================================*/

public class Deque<Item> implements Iterable<Item>{
	private class node {
		Item item;
		node prev;
		node next;
		}
	private node first;
	private node last;
	private int n;

	// construct an empty deque
	public Deque() {
		first=null;
		last=null;
		n=0;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return n==0;
	}

	// return the number of items on the deque
	public int size() {
		return n;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item==null) {
			throw new NullPointerException();
		}
		node oldfirst=first;
		nw=new node();
		nw.item=item;
		nw.next=oldfirst;
		if (isEmpty()) {
			last=first;
		}
		else {
			oldfirst.prev=first;
		}
		n++;
	}

	// add the item to the end
	public void addEnd(Item item) {
		if (item==null) {
			throw new NullPointerException();
		}
		node oldlast=last;
		last=new node();
		last.item=item;
		last.prev=oldlast;
		if (isEmpty()) {
			first=last;
		}
		else {
			oldlast.next=last;
		}
		n++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Item item=first.item;
		first=first.next;
		n--;
		if (isEmpty()) {
			last=null;
		}
		else {
			first.prev=null;
		}
		return item;
	}

	// remove and return the item from the end
	public Item removeLast() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Item item=last.item;
		last=last.prev;
		n--;
		if (isEmpty()) {
			first=null;
		}
		else {
			last.next = null;
		}
		return item;
	}

	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	private class RQIterator implements Iterator<Item> {
		private int index=0;
		private Item[] r;

		private RQIterator() {
			r = (Item[]) new Object[N];
			for(int i=0; i<N; i++)
			    r[i] = rq[i];
			StdRandom.shuffle(r);
		}
		public boolean hasNext() {
			return index < N;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if(!hasNext()) throw new java.util.NoSuchElementException();
			Item  item = r[index++];
			return item;
		}
	}

	// public static void main(String[] args)   // unit testing (optional)
}

