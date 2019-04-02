/*
Name: Beier (Benjamin) Liu
Date: 6/27/2018

Remark:
*/

import java.util.Arrays;
import edu.princeton.cs.algs4.ResizingArrayBag;

/*===================================================================================================
File content:
A FastCollinearPoints Class
===================================================================================================*/

public class FastCollinearPoints {
	private int count;
	private ResizingArrayBag<LineSegment> arr;

	public FastCollinearPoints(Point[] points)
	{
		count = 0;
		arr = new ResizingArrayBag<LineSegment>();
		if (points == null) { throw new IllegalArgumentException("U have null constructor issue"); }
		Point[] copy_points = points.clone();

		for (int i = 0; i < copy_points.length; i++) {
			if (copy_points[i] == null) { throw new IllegalArgumentException("U have null pointers issue"); }
			if (i > 0 && copy_points[i].compareTo(copy_points[i-1]) == 0) { throw new IllegalArgumentException("U have duplicate points issue"); }
		}

		for (int fst = 0; fst<copy_points.length-3; fst++)
		{
			Arrays.sort(copy_points);
			Arrays.sort(copy_points, copy_points[fst].slopeOrder());

			for (int curr = 0, first = 1, last = 2; last < copy_points.length; last++)
			{
				while (last < copy_points.length && Double.compare(copy_points[curr].slopeTo(copy_points[first]), copy_points[curr].slopeTo(copy_points[last])) == 0) { last++; }

				if (last - first >= 3 && copy_points[curr].compareTo(copy_points[first]) < 0)
				{
					arr.add(new LineSegment(copy_points[curr], copy_points[last - 1]));
					count = count+1;
				}

				first = last;
			}
		}
	}

	public int numberOfSegments() { return count; }

	public LineSegment[] segments()
	{
		LineSegment[] res = new LineSegment[numberOfSegments()];
		int idx = 0;
		for (LineSegment lsg : arr)
		{
			res[idx] = lsg;
			idx = idx+1;
		}
		return res;
	}

	public static void main(String[] args) {
		/* YOUR CODE HERE */
	}
}
