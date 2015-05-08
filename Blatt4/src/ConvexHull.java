import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConvexHull
{
	public static void main(String[] args)
	{
		Point[] points= GenerateTestPoints();

		System.out.println("Computing convex hull...");
		List<Point> convexHull = simpleConvex(points);

		System.out.println("The corners of the convex hull are (clockwise):");
		for (int i = 0; i < convexHull.size(); i++)
			System.out.println(convexHull.get(i).get(0) + ", " + convexHull.get(i).get(1));
	}

	/*
	 * Fills a triangle with points so that the convex hull of the generated points is always the bounding triangle
	 */
	private static Point[] GenerateTestPoints()
	{
		Point[] points = new Point[1003];
			points[0] = new Point(10, 10);
			points[1] = new Point(10, 100);
			points[2] = new Point(100, 10);

			System.out.println("Generating random points inside a triangle...");

			Random random = new Random();
			for (int i = 3; i < points.length; i++)
			{
				double xPos = random.nextDouble() * 90 + 10;
				double yPos = 10 + (100 - xPos) * random.nextDouble();

				Point nextPoint = new Point(xPos, yPos);

				points[i] = nextPoint;
			}
			
			return points;
	}
	
	private static Point[] GenerateRandomPoints()
	{
		Point[] points = new Point[1000];

			System.out.println("Generating random points...");

			Random random = new Random();
			for (int i = 0; i < points.length; i++)
			{
				double xPos = random.nextDouble() * 100-50;
				double yPos = random.nextDouble() * 100-50;
				
				Point nextPoint = new Point(xPos, yPos);

				points[i] = nextPoint;
			}
			
			return points;
	}
	

	/*
	 * computes the convex hull of the given point cloud
	 */
	public static List<Point> simpleConvex(Point[] P)
	{
		List<Point[]> edges = new ArrayList<Point[]>();

		for (int i = 0; i < P.length; i++)
		{
			for (int j = 0; j < P.length; j++)
			{
				if (i == j)
					continue;

				// connect two points by a line
				boolean valid = true;

				// check whether all remaining points are on the right side of the line
				for (int r = 0; r < P.length; r++)
				{
					if (r == i || r == j)
						continue;

					if (isLeftOf(P[i], P[j], P[r]))
					{
						valid = false;
						break;
					}
				}
				if (valid)// we found a valid edge of the convex hull
					edges.add(new Point[] { P[i], P[j] });
			}
		}

		
		 //Prints all found edges of the hull
		 //for (int i = 0; i < edges.size(); i++)
		 //	System.out.println(edges.get(i)[0].get(0) + ", " + edges.get(i)[0].get(1) + " ; " + edges.get(i)[1].get(0) + ", " + edges.get(i)[1].get(1));

		List<Point> returnValue = new ArrayList<Point>();

		returnValue.add(edges.get(0)[1]);// use any one of the points of the first edge as starting point
		edges.remove(0);

		// connect all edges of the convex hull
		while (!edges.isEmpty())
		{
			for (int i = 0; i < edges.size(); i++)
				if (edges.get(i)[0] == returnValue.get(returnValue.size() - 1))// find the edge that starts with the last known point of the convex hull
				{
					returnValue.add(edges.get(i)[1]);
					edges.remove(i);
					break;
				}
		}

		return returnValue;
	}

	/*
	 * Determines whether the given point is left of the straight line
	 */
	static boolean isLeftOf(Point linePoint1, Point linePoint2, Point point)
	{
		double slope = (linePoint2.get(1) - linePoint1.get(1)) / (linePoint2.get(0) - linePoint1.get(0));// (y1-y2)/(x1-x2)=slope
		double yIntercept = linePoint1.get(1) - linePoint1.get(0) * slope;// y-x*slope=yIntercept
		double dir = Math.signum(linePoint2.get(0) - linePoint1.get(0));// determines in which direction the line points (left-to-right: 1, right-to-left: -1)

		if (linePoint2.get(0) - linePoint1.get(0) == 0)// an infinite slope
			if (linePoint1.get(1) < linePoint2.get(1))
				return point.get(0) < linePoint1.get(0);// for a line pointing up, left is on the left side
			else
				return point.get(0) > linePoint1.get(0);// for a line pointing down, left is on the right side

		if (dir == 1)// if the line points from left to right, all point above the line are 'left' in respect to the line
			return slope * point.get(0) + yIntercept < point.get(1);
		else
			// if (slope < 0)// the opposite is true with a negative slope
			return slope * point.get(0) + yIntercept > point.get(1);
	}
}