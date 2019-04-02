import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private Board initial;
	private Node solvableNode;
	private MinPQ<Node> pq;
	private class Node implements Comparable<Node> 
	{
		private int key;
		private Board value;
		private Node parent;
		private int moves;
		public Node(int key, Board value) 
		{
			this.key = key;
			this.value = value;
			this.parent = null;
			this.moves = -1;
		}
		public Node(int key, Board value, Node parent) 
		{
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.moves = -1;
		}
		public Board getBoard() 
		{
			return this.value;
		}
		public Node getParent() 
		{
			return this.parent;
		}
		public Node getRoot() 
		{
			if (this.parent == null) 
			{
				return this;
			}
			return this.parent.getRoot();
		}
		public int getMoves() 
		{
			// for root, update the sum of moves
			if (this.moves == -1) 
			{
				if(this.parent == null) 
				{
					this.moves = 0;
				} else {
					this.moves = 1 + this.parent.getMoves();
				}
			}
			// for child nodes, return the roots
			return this.moves;
		}
		@Override
		public int compareTo(Node that) {
			// TODO Auto-generated method stub
			return Integer.compare(this.key, that.key);
		}
	}
	
	public Solver(Board initial) 
	{
		this.initial = initial;
		this.pq.insert(new Node(initial.manhattan(), initial));
		this.pq.insert(new Node(initial.twin().manhattan(), initial.twin()));
		
		while( findSolution() == false) {}
	}
	private boolean findSolution() 
	{
		Node min = pq.delMin();
		Board minBoard = min.getBoard();
		if( minBoard.isGoal() ) 
		{
			this.solvableNode = min;
			return true;
		}
		if( min.getParent() != null ) 
		{
			Board parentBoard = min.getParent().getBoard();
			for(Board neighbor: minBoard.neighbors()) 
			{
				if (!parentBoard.equals(neighbor)) {
					this.pq.insert(new Node(neighbor.manhattan()+min.getMoves()+1, neighbor));
				}
			}
		} else {
			for(Board neighbor: minBoard.neighbors()) 
			{
				this.pq.insert(new Node(neighbor.manhattan()+min.getMoves()+1, neighbor));
			}
		}
		return false;
	}
	public boolean isSolvable() 
	{ 
		return this.solvableNode.getRoot().getBoard() == initial; 
	}
	public int moves() 
	{ 
		if (isSolvable()) 
		{
			return this.solvableNode.getMoves();
		} else {
			return -1;
		}
	}
	public Iterable<Board> solution() 
	{ 
		if ( !isSolvable() ) 
		{
			return null;
		}
		Stack<Board> rtn = new Stack<Board>();
		Node curr = this.solvableNode;
		while( curr.getParent() != null ) 
		{
			rtn.push(curr.getBoard());
			curr = curr.getParent();
		}
		return rtn; 
	}
	public static void main(String[] args) 
	{
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++) 
		{
			for(int j = 0; j < n; j++) 
			{
				blocks[i][j] = in.readInt();
			}
		}
		Board initial = new Board(blocks);
		
		// Solve the puzzle 
		Solver solver = new Solver(initial);
		
		// print solution to standard output
		if( !solver.isSolvable() ) 
		{
			StdOut.println("No solution possible");
		} else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board: solver.solution() ) 
			{
				StdOut.println(board);
			}
		}
	}
}
