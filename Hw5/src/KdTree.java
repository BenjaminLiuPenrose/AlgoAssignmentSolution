import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
	private Node root;
	private RectHV plain;
	private int size;
	private class Node 
	{
		private Point2D p;
		private RectHV rect;
		private Node left;
		private Node right;
		private boolean vertical;
		public Node(Point2D p, boolean vertical, RectHV rect) 
		{
			this.p = p;
			this.vertical = vertical;
			this.rect = rect;
			this.left = null;
			this.right = null;
		}
	}
	public KdTree() 
	{
		this.root = null;
		this.plain = new RectHV(0, 0, 1, 1);
		this.size = 0;
	}
	public boolean isEmpty() 
	{
		return this.size == 0;
	}
	public int size() 
	{
		return this.size;
	}
	public void insert(Point2D p) 
	{
		if (p == null) 
		{
			throw new java.lang.NullPointerException("null pointer exception.");
		}
		this.root = insert(this.root, p, true, plain);
	}
	private Node insert(Node node, Point2D p, boolean vertical, RectHV rect) 
	{
		if (node == null) 
		{
			this.size += 1;
			return new Node(p, vertical, rect);
//			throw new java.lang.NullPointerException("null pointer exception.");
		}
		if( node.p.equals(p) ) 
		{
			return node;
		}
		boolean cmp;
		if ( vertical ) 
		{
			cmp = p.x() < node.p.x();
		} else {
			cmp = p.y() < node.p.x();
		}
		RectHV nextRect;
		if (cmp) 
		{
			if (node.left == null) 
			{
				double x1 = rect.xmin();
				double y1 = rect.ymin();
				double x2, y2;
				if( vertical ) 
				{
					x2 = node.p.x();
					y2 = rect.xmax();
				} else {
					x2 = rect.xmax();
					y2 = node.p.y();
				}
				nextRect = new RectHV(x1, y1, x2, y2);
			} else {
				nextRect = node.left.rect;
			}
			node.left = insert(node.left, p, !vertical, nextRect);
		} else {
			if (node.right == null) 
			{
				double x2 = rect.xmax();
				double y2 = rect.ymax();
				double x1, y1;
				if ( vertical ) 
				{
					x1 = node.p.x();
					y1 = rect.ymin();
				} else {
					x1 = rect.xmin();
					y1 = node.p.y();
				}
				nextRect = new RectHV(x1, y1, x2, y2);
			} else {
				nextRect = node.right.rect;
			}
			node.right = insert(node.right, p, !vertical, nextRect);
		}
		return node;
	}
	public boolean contains(Point2D p) 
	{
		if ( p == null ) 
		{
			throw new java.lang.NullPointerException("null pointer expection.");
		}
		return contains(this.root, p);
	}
	private boolean contains(Node node, Point2D p) 
	{
		if( node == null ) 
		{
			return false;
		}
		if (node.p.equals(p)) 
		{
			return true;
		}
		boolean cmp;
		if (node.vertical) 
		{
			cmp = p.x() < node.p.x();
		} else {
			cmp = p.y() < node.p.y();
		}
		if (cmp) 
		{
			return contains(node.left, p);
		} else {
			return contains(node.right, p);
		}
	}
	public void draw() 
	{
		draw(this.root, true);
	}
	
	private void draw(Node node, boolean vertical) 
	{
		if ( node == null ) 
		{
			throw new java.lang.NullPointerException("null pointer exception.");
		}
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		node.p.draw();
		if ( vertical ) 
		{
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
		}
		if ( node.left != null ) 
		{
			draw(node.left, !vertical);
		}
		if ( node.right != null )
		{
			draw(node.right, !vertical);
		}
	}
	public Iterable<Point2D> range(RectHV rect) 
	{
		if (rect == null ) 
		{
			throw new java.lang.NullPointerException("null pointer exception.");
		}
		Queue<Point2D> points = new Queue<Point2D>();
		Queue<Node> nodes = new Queue<Node>();
		nodes.enqueue(this.root);
		// BFS traversal
		while (!nodes.isEmpty()) 
		{
			Node node = nodes.dequeue();
			if(node == null) 
			{
				continue;
			}
			if (rect.contains(node.p)) 
			{
				points.enqueue(node.p);
			}
			if (node.left != null) 
			{
				nodes.enqueue(node.left);
			}
			if (node.right != null) 
			{
				nodes.enqueue(node.right);
			}
		}
		return points;
	}
	public Point2D nearest(Point2D p) 
	{
		if( p == null) 
		{
			throw new java.lang.NullPointerException("null pointer exception.");
		}
		Point2D rtn = null;
		double minDist = Double.NEGATIVE_INFINITY;
		Queue<Node> nodes = new Queue<Node>();
		nodes.enqueue(this.root);
		while( !nodes.isEmpty() ) 
		{
			Node node = nodes.dequeue();
			if (node == null) 
			{
				continue;
			}
			double dist = p.distanceTo(node.p);
			if (dist < minDist) 
			{
				rtn = node.p;
				minDist = dist;
			}
			if (node.left != null) 
			{
				nodes.enqueue(node.left);
			}
			if (node.right != null) 
			{
				nodes.enqueue(node.right);
			}
		}
		return rtn;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KdTree tree = new KdTree();

		StdOut.println("Is empty: " + tree.isEmpty());

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);

		In file = new In(args[0]);
		while (!file.isEmpty()) {
			Point2D point = new Point2D(file.readDouble(), file.readDouble());
			if (tree.contains(point)) {
				StdOut.println("Duplicate detected: " + point.toString());
			}
			tree.insert(point);
		}
		StdOut.println("Size: " + tree.size());
		StdOut.println("Is empty: " + tree.isEmpty());
		tree.draw();

		Point2D point = new Point2D(0.5, 1.0);
		StdOut.println("tree contains " + point.toString() + ": "
					   + tree.contains(point));

		RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);

		StdOut.println("Points in rectangle " + rect.toString() + ":");
		for (Point2D p : tree.range(rect)) {
			StdOut.println(p.toString());
		}
		StdOut.println("-");

		Point2D nearPoint = new Point2D(0.1, 0.1);

		StdOut.println("The nearest point to point " + nearPoint.toString() + ": ");
		StdOut.println(tree.nearest(nearPoint).toString());
		StdOut.println("-");
	}

}
