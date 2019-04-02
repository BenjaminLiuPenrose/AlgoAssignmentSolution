import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastColinearPoints {
	private int numberOfSegments;
	private ResizingArrayBag<LineSegment> lineSegmentBag;
	
	public FastColinearPoints(Point[] points) 
	{
		int count = 0;
		ResizingArrayBag<LineSegment> rtn = new ResizingArrayBag<LineSegment>();
		Point[] points_copy = points.clone();
		Arrays.sort(points_copy);
		for (int i = 0; i < points.length; i++) 
		{
			if(points_copy[i] == null) { throw new IllegalArgumentException("you have null pointers issue."); }
			if(i > 0 && points_copy[i].compareTo(points_copy[i-1]) == 0) { throw new IllegalArgumentException("you have duplicate points issue."); }
		}
		
		for (int first = 0; first < points_copy.length - 3; first++) 
		{
			Arrays.sort(points_copy, points_copy[first].slopeOrder());
			for (int curr = 0, second = 1, last = 2; last < points_copy.length ; last++) 
			{
				while(last < points_copy.length && Double.compare(
						points_copy[curr].slopTo(points_copy[second]), 
						points_copy[curr].slopTo(points_copy[last])
						) == 0
						) 
				{
					last++;
				}
				if (last - second >= 3 && points_copy[curr].compareTo(points_copy[first]) < 0) 
				{
					LineSegment ls = new LineSegment(points_copy[curr], points_copy[last - 1]);
					rtn.add(ls);
					count += 1;
				}
			}
			Arrays.sort(points_copy);
		}
			
		this.lineSegmentBag = rtn;
		this.numberOfSegments = count;
	}
	public int numberOfSegments() 
	{ 
		return this.numberOfSegments; 
	}
	public LineSegment[] segments() 
	{ 
		LineSegment[] rtn = new LineSegment[this.numberOfSegments];
		int idx = 0;
		for (LineSegment ls : this.lineSegmentBag) 
		{
			rtn[idx] = ls;
			idx += 1;
		}
		return rtn; 
	}
	
	public static void main(String[] args) 
	{
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i <n; i++) 
		{
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}
		
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p: points) 
		{
			p.draw();
		}
		StdDraw.show();
		
		FastColinearPoints colinear = new FastColinearPoints(points);
		for (LineSegment ls: colinear.segments()) 
		{
			StdOut.println(ls);
			ls.draw();
		}
	} 
}
