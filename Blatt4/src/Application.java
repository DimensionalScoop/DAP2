import java.util.Random;

public class Application
{
	static Triangle triangle;

	public static void main(String[] args)
	{
		try
		{
			parseArgs(args);
		} catch (IllegalArgumentException ex)
		{
			System.out.println("Invaild args.");
			System.exit(1);
		}

		if (triangle == null)
			generateRandomTriangle();
		
		System.out.println(triangle.perimeter());
	}

	private static void generateRandomTriangle()
	{
		Random random = new Random();
		triangle = new Triangle(
				new Point(random.nextInt(2001) - 1000, random.nextInt(2001) - 1000),
				new Point(random.nextInt(2001) - 1000, random.nextInt(2001) - 1000),
				new Point(random.nextInt(2001) - 1000, random.nextInt(2001) - 1000));
	}

	/*
	 * parses either nothing or a 6 doubles to a triangle
	 */
	static void parseArgs(String[] args)
	{
		if (args.length == 0)
		{
			triangle = null;
			return;
		}

		if (args.length != 6)
			throw new IllegalArgumentException();

		Point[] points = new Point[3];

		for (int i = 0; i < 3; i++)// parse three points to generate a triangle
		{
			double x = Double.parseDouble(args[i * 2]);
			double y = Double.parseDouble(args[i * 2 + 1]);

			points[i] = new Point(x, y);
		}
		triangle = new Triangle(points[0], points[1], points[2]);

		assert triangle.validate();
	}
}
