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

public class Deque<Item> implements Iterable<Item> {
	private class node {
		Item item;
		node prev;
		node next;
	}
	private node first;
	private node last;
	private int len;

	public Deque()
	{
		first = null;
		last = null;
		len = 0;
	}

	public boolean isEmpty() { return (len==0); }

	public int size() { return len; }

	public void addFirst(Item item)
	{
		if (item == null) { throw new java.lang.IllegalArgumentException(); }
		node old_first = first;
		first = new node();
		first.item = item;
		first.next = old_first;
		if (isEmpty()) { last = first; }
		else { old_first.prev = first; }
		len = len+1;
	}

	public void addLast(Item item)
	{
		if (item == null) { throw new java.lang.IllegalArgumentException(); }
		node old_last = last;
		last = new node();
		last.item = item;
		last.prev = old_last;
		if (isEmpty()) { first = last; }
		else { old_last.next = last; }
		len = len+1;
	}

	public Item removeFirst()
	{
		if (isEmpty()) { throw new java.util.NoSuchElementException(); }
		node nw = new node();
		nw = first;
		node nxt = first.next;
		first = nxt;
		len = len-1;
		if (isEmpty()) { last = null; }
		return nw.item;
	}

	public Item removeLast()
	{
		if (isEmpty()) { throw new java.util.NoSuchElementException(); }
		node nw = new node();
		nw = last;
		node pre = last.prev;
		last = pre;
		len = len-1;
		if (isEmpty()) { first = null; }
		return nw.item;
	}

	public Iterator<Item> iterator() { return new ListIterator(); }

	private class ListIterator implements Iterator<Item>
	{
		private node curr = first;
		private int index = 0;

		public boolean hasNext() { return (index < len); }

		public void remove() { throw new java.lang.UnsupportedOperationException(); }

		public Item next()
		{
			if (!hasNext()) { throw new java.util.NoSuchElementException(); }
			node nw = new node();
			nw = curr;
			curr = curr.next;
			index = index+1;
			return nw.item;
		}
	}


	public static void main(String[] args) {}

}

