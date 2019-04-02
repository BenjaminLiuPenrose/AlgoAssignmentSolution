/*
Name: Beier (Benjamin) Liu
Date: 6/28/2018

Remark:
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/*===================================================================================================
File content:
Solver Class
===================================================================================================*/

public class Solver {
 private MinPQ<Node> pq;
 private Node solution;
 private Board init;

 private class Node implements Comparable<Node>
 {
  private int key;
  private Board value;
  private Node parent;
  private int moves;

  Node(int key, Board value)
  {
   this.key = key;
   this.value = value;
   this.moves = -1;
  }

  Node(int key, Board value, Node parent)
  {
   this.key = key;
   this.value = value;
   this.parent = parent;
   this.moves = -1;
  }

  public int compareTo(Node that)
  {
   return Integer.compare(this.key, that.key);
  }

  public int getMoves()
  {
   if (moves == -1)
   {
    if (parent == null)
    {
     moves = 0;
    }
    else
    {
     moves = 1+parent.getMoves();
    }
   }
   return moves;
  }

  public Node getRoot()
  {
   if (parent == null) { return this; }
   return parent.getRoot();
  }

  public Board getBoard() { return value; }

  public Node getParent() { return parent; }
 }

 public Solver(Board initial)
 {
  init = initial;
  pq = new MinPQ<Node>();
  Board twin = initial.twin();
  pq.insert(new Node(init.manhattan(), init));
  pq.insert(new Node(twin.manhattan(), twin));

  // solver kernal
  while (dequeue() == false) {}
 }

 private boolean dequeue()
 {
  Node min = pq.delMin();
  Board minBoard = min.getBoard();
  if (minBoard.isGoal())
  {
   solution = min;
   return true;
  }

  if (!(min.getParent()==null))
  {
   Board parentBoard = min.getParent().getBoard();
   for(Board neighbor: minBoard.neighbors())
   {
    if(!(parentBoard.equals(neighbor)))
    {
     Node node = new Node(neighbor.manhattan()+min.getMoves()+1, neighbor, min);
     pq.insert(node);
    }
   }
  }
  else
  {
   for(Board neighbor: minBoard.neighbors())
   {
    Node node = new Node(neighbor.manhattan()+min.getMoves()+1, neighbor, min);
    pq.insert(node);
   }
  }
  return false;
 }

 public boolean isSolvable() { return init.equals(solution.getRoot().getBoard()); }
 public int moves()
 {
  if (isSolvable())
  {
   return solution.getMoves();
  }
  return -1;
 }

 public Iterable<Board> solution()
 {
  if (isSolvable())
  {
   Stack<Board> res = new Stack<Board>();
   Node curr = solution;
   while (curr!=null)
   {
    res.push(curr.getBoard());
    curr = curr.getParent();
   }
  }
  return null;
 }

 public static void main(String[] args) {}
}
