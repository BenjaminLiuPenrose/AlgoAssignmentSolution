import java.util.Arrays;
import edu.princeton.cs.algs4.ResizingArrayBag;

public class BruteColinearPoints {
	private int numberOfSegments;
	private ResizingArrayBag<LineSegment> lineSegmentBag;
	public BruteColinearPoints(Point[] points) 
	{
		int count = 0;
		ResizingArrayBag<LineSegment> rtn = new ResizingArrayBag<LineSegment>();
		Point[] points_copy = points.clone();
		Arrays.sort(points_copy);
		
		for (int i = 0; i< points_copy.length; i++) 
		{
			if (points_copy[i] == null) { throw new IllegalArgumentException("you have null pointers issue."); }
			if (i > 0 && points_copy[i].compareTo(points_copy[i-1]) == 0) { throw new IllegalArgumentException("you have duplicate points issue."); }
		}
		for (int first = 0; first < points_copy.length; first++) 
		{
			for (int second = first + 1; second < points_copy.length; second++) 
			{
				double slope_12 = points_copy[first].slopTo(points_copy[second]);
				for (int third = second + 1; third < points_copy.length; third++) 
				{
					double slope_23 = points_copy[second].slopTo(points_copy[third]);
					if (Double.compare(slope_12, slope_23) == 0) 
					{
						for (int fourth = third + 1; fourth < points_copy.length; fourth++) 
						{
							double slope_34 = points_copy[third].slopTo(points_copy[fourth]);
							if (Double.compare(slope_23, slope_34) == 0) 
							{
								LineSegment ls = new LineSegment(points_copy[first], points_copy[fourth]);
								rtn.add(ls);
								count += 1;
							}
							
						}
					}
				}
			}
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
}
