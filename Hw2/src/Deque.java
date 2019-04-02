import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>{
	private class Node
	{
		public Item item;
		public Node prev;
		public Node next;
		
		public Node() 
		{
			this.item = null;
			this.prev = null;
			this.next = null;
		}
	}
	
	private Node first;
	private Node last;
	private int length;
	private class ListIterator implements Iterator<Item>
	{
		private Node curr = first;
		private int idx = 0;
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return idx < length;
		}

		@Override
		public Item next() {
			// TODO Auto-generated method stub
			if (!hasNext()) 
			{
				throw new java.util.NoSuchElementException("no such element");
			}
			Node rtn = this.curr;
			this.curr = this.curr.next;
			this.idx += 1;
			return rtn.item;
		}
		
	}
	
	public Deque() 
	{
		this.first = new Node();
		this.last = new Node();
		this.length = 0;
	};
	
	public boolean isEmpty() 
	{ 
		return this.length == 0; 
	}
	public int size() 
	{ 
		return this.length; 
	}
	public void addFirst(Item item) 
	{
		Node first = new Node();
		first.item = item;
		first.next = this.first;
		if (isEmpty()) 
		{
			this.last.item = item;
		} else {
			this.first.prev = first;
		}
		this.first = first;
		this.length += 1;
	}
	public void addLast(Item item) 
	{
		Node last = new Node();
		last.item = item;
		last.prev = this.last;
		if (isEmpty()) 
		{
			this.first.item = item;
		} else {
			this.last.next = last;
		}
		this.last = last;
		this.length += 1;
	}
	public Item removeFirst() 
	{ 	
		if (isEmpty()) 
		{
			throw new java.util.NoSuchElementException("no such element");
		}
		Node rtn = this.first;
		if (size() == 1) {
			this.first = new Node();
			this.last = new Node();
		} else {
			this.first = this.first.next;
		}
		this.length -= 1;
		return rtn.item;
	}
	public Item removeLast() 
	{ 
		if (isEmpty()) 
		{
			throw new java.util.NoSuchElementException("no such element");
		} 
		Node rtn = this.last;
		if (size() == 1) 
		{
			this.first = new Node();
			this.last = new Node();
		} else {
			this.last = this.last.prev;
		}
		this.length -= 1;
		return rtn.item;
	}
	
	
	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new ListIterator();
	};
	
	public static void main(String[] args) 
	{
		boolean isUnit = false;
	};
}
