/*
Name: Beier (Benjamin) Liu
Date: 6/25/2018

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
	private Item[] arr;
	private int len;
	private static int INIT_SIZE = 2;

	public RandomizedQueue() {
		arr = (Item[]) new Object[INIT_SIZE];
		len = 0;
	}

	public boolean isEmpty() { return (len == 0); }

	public int size() { return len; }

	private void resize(int new_size)
	{
		Item[] temp = (Item[]) new Object[new_size];
		for (int i=0; i<arr.length; i++) { temp[i]=arr[i]; }
		arr = temp;
	}

	public void enqueue(Item item)
	{
		if (item == null) { throw new java.lang.NullPointerException(); }
		if (len == arr.length) { resize(2*arr.length); }
		arr[len] = item;
		len = len+1;
	}

	public Item dequeue()
	{
		if (isEmpty()) { throw new java.util.NoSuchElementException(); }
		int random_sample = StdRandom.uniform(len);
		Item sample = arr[random_sample];
		arr[random_sample]=arr[len-1];
		len = len-1;
		if (len < arr.length/4) { resize(2*(arr.length/4)); }
		return sample;
	}

	public Item sample()
	{
		if (isEmpty()) { throw new java.util.NoSuchElementException(); }
		int random_sample = StdRandom.uniform(len);
		Item sample = arr[random_sample];
		return sample;
	}

	public Iterator<Item> iterator() { return new ArrayIterator(); }

	private class ArrayIterator implements Iterator<Item>
	{
		private int index = 0;
		private int[] random;

		public ArrayIterator() {
			random = new int[len];
			for (int i = 0; i < random.length; i++) {
				random[i] = i;
			}
			StdRandom.shuffle(random);
		}

		public boolean hasNext() { return index < random.length; }

		public void remove() { throw new java.lang.UnsupportedOperationException(); }

		public Item next()
		{
			if (!hasNext()) { throw new java.util.NoSuchElementException(); }
			int randomIndex = random[index];
			index++;
			return arr[randomIndex];
		}
	}

	public static void main(String[] args) {}

}

