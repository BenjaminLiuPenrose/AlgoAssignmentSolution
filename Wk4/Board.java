/*
Name: Beier (Benjamin) Liu
Date: 6/28/2018

Remark:
*/

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

/*===================================================================================================
File content:
Board Class
===================================================================================================*/

public class Board {
	private char[] square;
	private int dim;
	private int pos_0;

	public Board(int[][] blocks)
	{
		dim = blocks.length;
		square = new char[dim*dim];

		for (int i=0; i<dim; i++)
		{
			for (int j=0; j<dim; j++)
			{
				// if (blocks[i][j] == 0) { square[dim*i+j]='-'; }
				// else { square[dim*i+j] = (char) blocks[i][j]; }
				square[dim*i+j] = (char) blocks[i][j];
			}
		}
	}

	public int dimension() { return dim; }

	public int hamming()
	{
		int len = dim*dim;
		int score_hamming = 0;
		for (int i=0; i<len; i++)
		{
			if ((int) square[i] != 0&&(int) square[i] != i+1) { score_hamming = score_hamming+1; }
		}
		return score_hamming;
	}

	public int manhattan()
	{
		int len = dim*dim;
		int score_manhattan = 0;
		for (int i=0; i<len; i++)
		{
			if ((int) square[i] != 0)
			{
				int row_target = i/dim;
				int col_target = i%dim;
				int elem = (int) square[i];
				int row_curr = elem/dim;
				int col_curr = elem%dim;
				int acc = Math.abs(row_target-row_curr)+Math.abs(col_target-col_curr);
				score_manhattan = score_manhattan + acc;
			}
		}
		return score_manhattan;
	}

	public boolean isGoal() { return (hamming()==0); }

	public Board twin()
	{
		int[][] twin_board = new int[dim][dim];
		boolean twined = false;
		for (int i=0; i<dim; i++)
		{
			for (int j=0; j<dim; j++)
			{
				// if (square[i*dim+j]=='-')
					// twin_board[i][j] = 0;
				// else
				twin_board[i][j] = (int) square[i*dim+j];
				if ((!twined)&&(j>0)&&(twin_board[i][j]!=0)&&(twin_board[i][j-1]!=0))
				{
					int temp = twin_board[i][j];
					twin_board[i][j] = twin_board[i][j-1];
					twin_board[i][j-1] = temp;
					twined = true;
				}
			}
		}
		return new Board(twin_board);
	}

	public boolean equals(Object y)
	{
		if (y == null) { return false; }
		if (y == this) { return true; }
		if (y.getClass() != this.getClass()) { return false; }

		Board that = (Board) y;
		if (that.dimension() != dim) { return false; }
		int len = dim*dim;
		for (int i=0; i<len; i++)
		{
			if (that.square[i] != this.square[i]) { return false; }
		}
		return true;
	}

	public Iterable<Board> neighbors()
	{
		Bag<Board> neighbors = new Bag<Board>();
		int pos_0 = -1;
		for (int i=0; i<dim; i++)
		{
			for (int j=0; j<dim; j++)
			{
				if (square[i*dim+j]=='0') { pos_0 = i*dim+j; }
			}
		}
		if (pos_0/dim>0) { neighbors.add(exch(pos_0, pos_0-dim)); } //pos_0-dim
		if (pos_0%dim>0) { neighbors.add(exch(pos_0, pos_0-1)); } //pos_0-1
		if (pos_0%dim<dim-1) { neighbors.add(exch(pos_0, pos_0+1)); } //pos_0+1
		if (pos_0/dim<dim-1) { neighbors.add(exch(pos_0, pos_0+dim)); } //pos_0+dim
		return neighbors;
	}

	private Board exch(int pos_1, int pos_2)
	{
		int[][] exch_board = new int[dim][dim];
		boolean exched = false;
		if (pos_1 > pos_2)
		{
			int tmp = pos_1;
			pos_1 = pos_2;
			pos_2 = tmp;
		}
		for (int i=0; i<dim; i++)
		{
			for (int j=0; j<dim; j++)
			{
				// if (square[i*dim+j]=='-')
					// exch_board[i][j] = 0;
				// else
				exch_board[i][j] = (int) square[i*dim+j];
				if ((!exched)&&(i*dim+j)==pos_1)
				{
					int i_pos_2 = pos_2/dim;
					int j_pos_2 = pos_2%dim;
					int temp = exch_board[i][j];
					// int temp2 = exch_board[i_pos_2][j_pos_2];
					exch_board[i][j] = exch_board[i_pos_2][j_pos_2];
					exch_board[i_pos_2][j_pos_2] = temp;
					exched = true;
				}
			}
		}
		return new Board(exch_board);
	}

	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append(dim+"\n");

		for (int i=0; i<dim; i++)
		{
			for (int j=0; j<dim; j++)
			{
				s.append(String.format("%2d ", (int) square[i*dim+j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {}
}
