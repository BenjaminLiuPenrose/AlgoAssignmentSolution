import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;


public class Point implements Comparable<Point>{
	private int x;
	private int y;
	private class slopeComparator implements Comparator<Point>
	{
		@Override
		public int compare(Point p, Point q) {
			// TODO Auto-generated method stub
			double pSlope = compareTo(p);
			double qSlope = compareTo(q);
			return Double.compare(pSlope, qSlope);
		}
		
	}
	public Point(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	public void draw() 
	{
		StdDraw.point(this.x, this.y);
	}
	public void drawTo(Point that) 
	{
		StdDraw.line(this.x, this.y, that.x, that.y);
	}
	public String toString() 
	{
		return "("+this.x+","+this.y+")";
	}
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	@Override
	public int compareTo(Point that) {
		// TODO Auto-generated method stub
		if(this.y > that.y) 
		{
			return 1;
		} else if (this.y < that.y) {
			return -1;
		} else if (this.x > that.x) {
			return 1;
		} else if (this.x < that.x) {
			return -1;
		} else {
			return 0;
		}
	}
	public double slopTo(Point that) 
	{
		if (this.x == that.x && this.y != that.y) 
		{
			return Double.POSITIVE_INFINITY;
		}
		if (this.x == that.x && this.y == that.y) 
		{
			return Double.NEGATIVE_INFINITY;
		}
		double rtn;
		rtn = ((that.y - this.y)* 1.0) / (that.x - this.x);
		return rtn;
	}
	public Comparator<Point> slopeOrder() 
	{
		return new slopeComparator();
	}
	
	
}
