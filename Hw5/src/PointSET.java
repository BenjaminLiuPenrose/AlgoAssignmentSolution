import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;

public class PointSET {
	private TreeSet<Point2D> set;
	
	public PointSET() 
	{
		this.set = new TreeSet<Point2D>();
	}
	public boolean isEmpty() 
	{
		return this.set.isEmpty();
	}
	public int size() 
	{
		return this.set.size();
	}
	public void insert(Point2D p) 
	{
		if (p == null) 
		{
			throw new java.lang.NullPointerException("null pointer.");
		}
		this.set.add(p);
	}
	public boolean contains(Point2D p) 
	{
		if (p == null) 
		{
			throw new java.lang.NullPointerException("null pointer exception.");
		}		
		return this.set.contains(p);
	}
	public void draw() 
	{
		for (Point2D p : this.set) 
		{
			p.draw();
		}
	}
	public Iterable<Point2D> range(RectHV rect) 
	{
		if(rect == null) 
		{
			throw new java.lang.NullPointerException("null pointer exception.");
		}
		Queue<Point2D> rtn = new Queue<Point2D>();
		for (Point2D p: this.set) 
		{
			if(rect.contains(p)) 
			{
				rtn.enqueue(p);
			}
		}
		return rtn;	
	}
	public Point2D nearest(Point2D p) 
	{
		if ( p == null ) 
		{
			throw new java.lang.NullPointerException("null pointer exception.");
		}
		double minDist = Double.NEGATIVE_INFINITY;
		Point2D rtn = null;
		for (Point2D pt : this.set) 
		{
			if(rtn == null || p.distanceTo(pt) < minDist) 
			{
				rtn = pt;
				minDist = p.distanceTo(pt);
			}
		}
		return rtn;
	}
	
	public static void main(String[] args) 
	{}
	
}