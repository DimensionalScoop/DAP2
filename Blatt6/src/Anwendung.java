import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Anwendung
{

	public static void main(String[] args)
	{
		ArrayList<Interval> intervals = parseFile(args);
		intervalScheduling(intervals);
	}

	/*
	 * Reads command line params and returns the intervals parsed from a file
	 */
	private static ArrayList<Interval> parseFile(String[] args)
	{
		ArrayList<Interval> returnValue = new ArrayList<>();
		RandomAccessFile file =null;
		
		try
		{
			if (args.length != 1)
				throw new IllegalArgumentException();

			String line;
			file = new RandomAccessFile(args[0], "r");
			System.out.println("Bearbeite Datei \"" + args[0] + "\".\n");

			while ((line = file.readLine()) != null)// read all lines from the file as intervals separated by ','
			{
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				returnValue.add(new Interval(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
			}

			printList("Es wurden " + returnValue.size() + " Zeilen mit folgendem Inhalt gelesen:", returnValue);
		} catch (NumberFormatException | NoSuchElementException n)
		{
			System.err.println("Badly formated file.");
			System.exit(1);
		} catch (IllegalArgumentException e)
		{
			System.err.println("Usage:\nAnwendung PathToFile");
			System.exit(1);
		} catch (IOException e2)
		{
			System.err.println("File not found.");
			System.exit(1);
		}
		finally{
			if(file!=null)
				try
				{
					file.close();
				} catch (IOException e)
				{
					System.err.println("Runtime Error");
					System.exit(1);
					e.printStackTrace();
				}
		}

		return returnValue;
	}

	/*
	 * Prints the message and all elements of the given list
	 */
	private static void printList(String message, ArrayList<Interval> list)
	{
		System.out.print(message + "\n[");
		for (int i = 0; i < list.size(); i++)
		{
			if (i > 0)
				System.out.print(", ");
			System.out.print(list.get(i).toString());
		}
		System.out.println("]\n");
	}

	/*
	 * Removes as few intervals as possible so that no two intervals overlap. Prefers intervals that end soon.
	 */
	public static ArrayList<Interval> intervalScheduling(ArrayList<Interval> intervals)
	{
		ArrayList<Interval> returnValue = new ArrayList<>();

		Collections.sort(intervals);//sorts by end values into ascending order
		printList("Sortiert:", intervals);

		int end = Integer.MIN_VALUE;//there is no previous task so the end of 'the last task' is the smallest number possible

		for (int i = 0; i < intervals.size(); i++)
		{
			if (end <= intervals.get(i).start)// if the last tasks starts before this task starts, skip this task
			{
				returnValue.add(intervals.get(i));
				end = intervals.get(i).end;
				
				for(int j=1;j<returnValue.size();j++)
					assert returnValue.get(j-1).end<=returnValue.get(j).start;//check for overlap
			}	
		}

		printList("Berechnetes Intervallscheduling:", returnValue);
		return returnValue;
	}
}
