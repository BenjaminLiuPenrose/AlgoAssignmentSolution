import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{
	private Item[] arr;
	private int length;
	private final int init_size = 2;
	private class ArrIterator<Item> implements Iterator<Item>
	{
		private int curr = 0;	
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return curr < length;
		}
		@Override
		public Item next() {
			// TODO Auto-generated method stub
			Item rtn = (Item) arr[this.curr];
			this.curr += 1;
			return rtn;
		}
	}
	
	public RandomizedQueue() 
	{
		arr = (Item[]) new Object[this.init_size];
		length = 0;
	}
	public boolean isEmpty() 
	{
		return this.length == 0;
	}
	public int size() 
	{
		return this.length;
	}
	public void enqueue(Item item) 
	{
		if (item == null) 
		{
			throw new java.lang.NullPointerException("null pointer");
		}
		if (this.arr.length <= this.length) 
		{
			resize(this.arr.length * 2);
		}
		this.arr[this.length] = item;
		this.length += 1;
	}
	private void resize(int size) 
	{
		Item[] tmp = (Item[]) new Object[size];
		for (int i = 0; i < this.length; i++) 
		{
			tmp[i] = this.arr[i];
		}
		this.arr = tmp;
	}
	public Item dequeue() 
	{
		if (isEmpty()) 
		{
			throw new java.util.NoSuchElementException("no such element");
		}
		int idx = StdRandom.uniform(0, this.length);
		Item rtn = this.arr[idx];
		for (int i = idx + 1; i < this.length; i++) 
		{
			this.arr[i - 1] = this.arr[i];
		}
		if (this.arr.length >= this.length / 4) 
		{
			resize(this.arr.length / 2);
		}
		this.length -= 1;
		return rtn; 
	}
	public Item sample() 
	{ 
		if (isEmpty()) 
		{
			throw new java.util.NoSuchElementException("no such element");
		}
		int idx = StdRandom.uniform(0, this.length);
		Item rtn = this.arr[idx];
		return rtn; 
	}
	
	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new ArrIterator();
	}
	
	public static void main(String[] args) 
	{
		boolean isUnit = false;
	}
	
}
