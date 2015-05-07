public class Triangle extends Simplex
{

	public Triangle(Point point, Point point2, Point point3)
	{
		super(point,point2,point3);
	}

	/*
	 * Checks whether this simplex is valid 2d triangle
	 * @see Simplex#validate()
	 */
	@Override
	public boolean validate()
	{
		if (corners.length != 3)
			return false;
		for (int i = 0; i < 3; i++)
			if (corners[i].dim != 2)
				return false;

		return true;
	}

}
