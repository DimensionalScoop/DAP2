public class Point
{
	int dim;
	double[] components;

	public Point(double... values)
	{
		if (values.length == 0)
			throw new IllegalArgumentException();

		components = values;
		dim = values.length;
	}

	public double get(int i)
	{
		if (i < 0 || i >= dim)
			throw new IllegalArgumentException();
		return components[i];
	}

	public int dim()
	{
		return dim;
	}

	/*
	 * Determines the euclidean distance between two points
	 */
	public double distance(Point ref)
	{
		if (dim != ref.dim)
			throw new IllegalArgumentException();

		double returnValue = 0;

		for (int i = 0; i < dim; i++)
			returnValue += Math.pow(get(i) - ref.get(i), 2);

		return Math.sqrt(returnValue);
	}
}