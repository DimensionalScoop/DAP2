import java.util.Random;

public class Main
{
	enum sortMethod
	{
		insert, merge
	}

	enum fillMethod
	{
		rand, asc, desc
	}

	static int arraySize = 0;
	static sortMethod sortFunction = sortMethod.insert;
	static fillMethod fillFunction = fillMethod.rand;


	public static void main(String[] args)
	{
		parseArgs(args);

		int[] array = new int[arraySize];
		
		fillArray(array);

		long time=sortArray(array);
		
		System.out.println("Sort time: "+time+"ms");
		
		if(Sortierung.isSorted(array))
			System.out.println("Feld sortiert!");
		else 
			System.out.println("Feld NICHT sortiert!");
		
		if(arraySize<101)
			for(int i=0;i<arraySize;i++)
				System.out.println(" "+array[i]);
		
	}

	static long sortArray(int[] array)//sorts array and returns the time needed to do so
	{
		switch (sortFunction)
		{
		case insert:
			long start=System.currentTimeMillis();
			Sortierung.insertionSort(array);
			return System.currentTimeMillis()-start;

		case merge:
			long startM=System.currentTimeMillis();
			Sortierung.mergeSort(array);
			return System.currentTimeMillis()-startM;
		}
		
		return -1;
	}

	static void fillArray(int[] array)
	{
		switch (fillFunction)
		{
		case rand:
			Random random = new Random();
			for (int i = 0; i < arraySize; i++)
				array[i] = random.nextInt();
			break;

		case asc:
			for (int i = 0; i < arraySize; i++)
				array[i] = i;
			assert Sortierung.isSorted(array);
			break;

		case desc:
			for (int i = 0; i < arraySize; i++)
				array[i] = arraySize - i;
			break;
		}
	}

	static void parseArgs(String[] args)
	{
		int index = 0;// the index of the argument that is being processed

		try
		{
			arraySize = Integer.parseInt(args[index]);
			index++;

			if (args.length == index)// end parsing when we run out of arguments to parse
				return;

			switch (args[index])
			{
			case "insert":
				sortFunction = sortMethod.insert;
				index++;
				break;

			case "merge":
				sortFunction = sortMethod.merge;
				index++;
				break;

			default:
				break;
			}

			if (args.length == index)// end parsing when we run out of arguments to parse
				return;

			switch (args[index])
			{
			case "auf":
				fillFunction = fillMethod.asc;
				break;

			case "ab":
				fillFunction = fillMethod.desc;
				break;

			case "rand":
				fillFunction = fillMethod.rand;
				break;

			default:
				throw new Exception();// the current argument exist, but cannot be parsed
			}
		} catch (Exception e)
		{
			System.out.println("Usage:\nSortierung 10000 [insert|merge [auf|ab|rand]]");
			System.exit(1);
		}

	}
}
