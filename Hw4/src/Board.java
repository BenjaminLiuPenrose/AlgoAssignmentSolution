import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private int[] square;
	private int dimension;
	private int length;
	private int pos_0;
	public Board(int[][] blocks) 
	{ 
		this.dimension = blocks.length;
		this.length = this.dimension * this.dimension;
		this.square = new int[this.length];
		for (int i = 0; i < this.dimension; i++) 
		{
			for (int j = 0; j < this.dimension; j++) 
			{
				this.square[i*this.dimension + j] = blocks[i][j];
				if (blocks[i][j] == 0) 
				{
					this.pos_0 = i * this.dimension + j;
				}
			}
		}
	}
	public int dimension() 
	{ 
		return this.dimension; 
	}
	public int hamming() 
	{ 
		int hamming = 0;
		for (int i = 0; i < this.length; i++) 
		{
			if (this.square[i] != i+1 && this.square[i] != 0) 
			{
				hamming += 1;
			}
		}
		return hamming; 
	}
	public int manhattan() 
	{
		int manhattan = 0;
		for (int i = 0; i < this.length; i++) 
		{
			if(this.square[i] != i+1 && this.square[i] != 0) 
			{
				int cpos = i;
				int tpos = this.square[i] - 1;
				int cy = cpos % this.dimension;
				int cx = cpos - cy * this.dimension;
				int ty = tpos % this.dimension;
				int tx = tpos - ty * this.dimension;
				manhattan += Math.abs(cx - tx) + Math.abs(cy - ty);
			}
		}
		return manhattan; 
	}
	public boolean isGoal() 
	{ 
		return hamming() == 0; 
	}
	public Board twin() 
	{ 
		int[] twinArr = this.square;
		int[][] twinSqr = new int[this.dimension][this.dimension];
		int tmp = 0;
		if ( twinArr[0] != 0) 
		{
			if ( twinArr[1] != 0) 
			{
				tmp = twinArr[1]; twinArr[1] = twinArr[0]; twinArr[0] = tmp; 
			} else {
				tmp = twinArr[this.dimension]; twinArr[this.dimension] = twinArr[0]; twinArr[0] = tmp;
			}
		} else {
			if( twinArr[this.length - 2] != 0) 
			{
				tmp = twinArr[this.length - 2]; twinArr[this.length - 2] = twinArr[this.length - 1]; twinArr[this.length - 1] = tmp;
			} else {
				tmp = twinArr[this.length - 1 - this.dimension]; twinArr[this.length - 1 - this.dimension] = twinArr[this.length - 1]; twinArr[this.length - 1] = tmp;
			}
		}
		for( int i = 0; i < this.length; i++) 
		{
			int cy = i % this.dimension;
			int cx = i - cy * this.dimension;
			twinSqr[cx][cy] = twinArr[i];
		}
		return new Board(twinSqr); 
	}
	public boolean equals(Object y) 
	{ 
		if (y == null) { return false; }
		if (y == this) { return true; }
		if (y.getClass() != this.getClass()) { return false; }
		Board that = (Board) y;
		if( that.dimension != this.dimension) { return false; }
		for(int i = 0; i < this.length; i++) 
		{
			if (this.square[i] != that.square[i]) { return false; }
		}
		return true; 
	}
	public Iterable<Board> neighbors() 
	{
		Bag<Board> neighbors = new Bag<Board>();
		if (this.pos_0 / this.dimension > 0) 
		{
			neighbors.add(exch(this.pos_0, this.pos_0 - this.dimension));
		} 
		if (this.pos_0 / this.dimension < this.dimension - 1) 
		{
			neighbors.add(exch(this.pos_0, this.pos_0 + this.dimension));
		}
		if (this.pos_0 % this.dimension > 0) 
		{
			neighbors.add(exch(this.pos_0, this.pos_0 - 1));
		}
		if (this.pos_0 % this.dimension < this.dimension - 1) 
		{
			neighbors.add(exch(this.pos_0, this.pos_0 + 1));
		}
		return neighbors; 
	}
	
	public String toString() 
	{
		StringBuilder s = new StringBuilder();
		s.append(this.dimension + "\n");
		
		for (int i = 0; i < this.dimension; i++) 
		{
			for ( int j = 0; j < this.dimension; j++) 
			{
				s.append(this.square[i*this.dimension + j]);
			}
			s.append("\n");
		}
		return s.toString(); 
	}
	
	private Board exch(int pos_1, int pos_2) 
	{
		int[] exchArr = this.square;
		int[][] exchSqr = new int[this.dimension][this.dimension];
		int tmp = 0;
		tmp = exchArr[pos_1]; exchArr[pos_1] = exchArr[pos_2]; exchArr[pos_2] = tmp;
		for( int i = 0; i < this.length; i++) 
		{
			int cy = i % this.dimension;
			int cx = i - cy * this.dimension;
			exchSqr[cx][cy] = exchArr[i];
		}
		return new Board(exchSqr);
	}
	
	public static void main(String[] args) {}
	
}
