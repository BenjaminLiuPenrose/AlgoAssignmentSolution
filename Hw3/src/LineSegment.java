import edu.princeton.cs.algs4.StdDraw;

public class LineSegment {
	private Point p1;
	private Point p2;
	public LineSegment(Point p, Point q) 
	{	
		this.p1 = p;
		this.p2 = q;
	}
	public void draw() 
	{
		//StdDraw.line(this.p1.getX(), this.p1.getY(), this.p2.getX(), this.p2.getY());
		this.p1.drawTo(this.p2);
	}
	public String toString() 
	{
		return this.p1.toString() + "->" + this.p2.toString();
	}
}
