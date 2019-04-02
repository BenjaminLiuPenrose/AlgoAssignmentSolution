package Percolatiion;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF gridUF;
	private boolean[] gridState;
	private int size;
	private int length;
	
	public Percolation(int n) 
	{
		if (n <= 0) 
		{
			throw new IllegalArgumentException("n must be at least 1");
		}
		this.size = n * n;
		this.length = n;
		this.gridUF = new WeightedQuickUnionUF(this.size + 2);
		this.gridState = new boolean[this.size + 2];
		
		this.gridState[0] = true; this.gridState[this.size + 1] = true;
		for (int i = 1; i <= this.size; i++) 
		{
			this.gridState[i] = false;
		}
	}
	
	public void open(int row, int col) 
	{
		if( row < 1 || row > this.length ) 
		{
			throw new IllegalArgumentException("row must be between 1 and n");
		}
		if( col < 1 || col > this.length ) 
		{
			throw new IllegalArgumentException("col must be between 1 and n");
		}
		
		int idx = gridToArray(row, col);
		this.gridState[idx] = true;
		
		if (col != 1 && isOpen(row, col - 1)) 
		{
			this.gridUF.union(idx, gridToArray(row, col - 1));
		}
		if (col != this.length && isOpen(row, col + 1))
		{
			this.gridUF.union(idx, gridToArray(row, col + 1));
		}
		if (row != 1 && isOpen(row - 1, col)) 
		{
			this.gridUF.union(idx, gridToArray(row - 1, col));
		}
		if (row != this.length && isOpen(row + 1, col)) 
		{
			this.gridUF.union(idx, gridToArray(row + 1, col));
		}
		if (row == 1) 
		{
			this.gridUF.union(idx, 0);
		}
		if (row == this.length) 
		{
			this.gridUF.union(idx, this.size + 1);
		}
	}
	
	public boolean isOpen(int row, int col) 
	{
		int idx = gridToArray(row, col);
		return this.gridState[idx] == true;
	}
	
	public boolean isFull(int row, int col) 
	{
		int idx = gridToArray(row, col);
		return this.gridUF.connected(0, idx);
	}
	
	public int numberOfOpenSites() 
	{
		int numOfOpen = 0;
		int idx = 0;
		for (int r = 1; r <= this.length; r++) 
		{
			for (int c = 1; c <= this.length; c++) 
			{
				idx = gridToArray(r, c);
				if (this.gridState[idx] == true) 
				{
					numOfOpen += 1;
				}
			}
		}
		return numOfOpen;
	}
	
	public boolean percolates() 
	{
		return this.gridUF.connected(0, this.size + 1);
	}
	
	private int gridToArray(int row, int col) 
	{
		return (row - 1) * this.length + col;
	}
}
