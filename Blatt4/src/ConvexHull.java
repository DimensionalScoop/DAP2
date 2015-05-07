import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConvexHull
{
	public static void main(String[] args)
	{
		Point[] points = new Point[1003];
		points[0] = new Point(10, 10);
		points[1] = new Point(10, 100);
		points[2] = new Point(100, 10);

		System.out.println("Generating random points...");

		Random random = new Random();
		for (int i = 3; i < points.length; i++)
		{
			double xPos = random.nextDouble() * 90 + 10;
			double yPos = 10 + (100-xPos) * random.nextDouble();

			
			Point nextPoint = new Point(xPos, yPos);

			points[i] = nextPoint;
		}

		System.out.println("Computing convex hull...");
		List<Point> convexHull = simpleConvex(points);

		for (int i = 0; i < convexHull.size(); i++)
			System.out.println(convexHull.get(i).get(0) + ", " + convexHull.get(i).get(1));
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

		for (int i = 0; i < edges.size(); i++)
			System.out.println(edges.get(i)[0].get(0) + ", " + edges.get(i)[0].get(1) + " ; " + edges.get(i)[1].get(0) + ", " + edges.get(i)[1].get(1));

		List<Point> returnValue = new ArrayList<Point>();

		returnValue.add(edges.get(0)[1]);// use one of the points of the first edge as starting point

		// connect all edges of the convex hull
		while (!edges.isEmpty())
		{
			for (int i = 0; i < edges.size(); i++)
				if (edges.get(i)[0] == returnValue.get(returnValue.size() - 1))// find edge that starts with the last known point of the convex hull
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

		if (linePoint2.get(0) - linePoint1.get(0) == 0)// an infinite slope
			if (linePoint1.get(1) < linePoint2.get(1))// for a line pointing up, left is on the left side
				return point.get(0) < linePoint1.get(0);
			else
				// for a line pointing up, left is on the right side
				return point.get(0) > linePoint1.get(0);

		if (slope > 0)// if the slope is positive, points left of the line are also below the line
			return slope * point.get(0) + yIntercept > point.get(1);
		else if (slope < 0)// the opposite is true with a negative slope
			return slope * point.get(0) + yIntercept < point.get(1);
		else
		{
			if (linePoint1.get(0) < linePoint2.get(0))// on a line going from left to right parallel to the x-axis, the 'left' side is above
				return point.get(1) > linePoint1.get(1);
			else
				// for a line in the opposite direction, 'left' is below
				return point.get(1) < linePoint1.get(1);
		}

	}
}