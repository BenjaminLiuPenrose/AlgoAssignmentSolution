/*
Name: Beier (Benjamin) Liu
Date: 6/27/2018

Remark:
*/

import java.util.Arrays;
import edu.princeton.cs.algs4.ResizingArrayBag;

/*===================================================================================================
File content:
A BruteCollinearPoints Class
===================================================================================================*/

public class BruteCollinearPoints {
 private int count;
 private ResizingArrayBag<LineSegment> arr;

 public BruteCollinearPoints(Point[] points)
 {
  count = 0;
  Point[] copy_points = points.clone();
  Arrays.sort(copy_points);

  for (int first=0; first<copy_points.length; first++)
  {
   for (int second=first+1; second<copy_points.length; second++)
   {
    double slopeFS = copy_points[first].slopeTo(copy_points[second]);
    for (int third=second+1; third<copy_points.length; third++)
    {
     double slopeFT = copy_points[first].slopeTo(copy_points[third]);
     if (slopeFS == slopeFT )
     {
      for (int forth=third+1; forth<copy_points.length; forth++)
      {
       double slopeFF = copy_points[first].slopeTo(copy_points[forth]);
       if(slopeFS == slopeFF)
       {
        arr.add(new LineSegment(copy_points[first], copy_points[forth]));
        count = count+1;
       }
      }
     }

    }
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
}
