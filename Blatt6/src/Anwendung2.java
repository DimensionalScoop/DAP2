import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Anwendung2
{
	static ArrayList<TimeTuple> schedule = new ArrayList<>();

	enum OptType
	{
		Interval, Lateness
	};

	static OptType Algorithm;

	public static void main(String[] args)
	{
		parseParams(args);

		if (Algorithm == OptType.Interval)
		{
			ArrayList<Interval> intervals = new ArrayList<>();
			for (int i = 0; i < schedule.size(); i++)
			{
				intervals.add((Interval) schedule.get(i));
			}
			intervalScheduling(intervals);
		} else if (Algorithm == OptType.Lateness)
		{
			ArrayList<Job> jobs = new ArrayList<>();
			for (int i = 0; i < schedule.size(); i++)
			{
				jobs.add((Job) schedule.get(i));
			}
			latenessScheduling(jobs);
		} else
		{
			throw new RuntimeException();
		}
	}

	/*
	 * Reads and interprets command line params
	 */
	private static void parseParams(String[] args)
	{
		RandomAccessFile file = null;

		try
		{
			if (args.length != 2)
				throw new IllegalArgumentException();

			if (args[0].equals("Interval"))
				Algorithm = OptType.Interval;
			else if (args[0].equals("Lateness"))
				Algorithm = OptType.Lateness;
			else
				throw new IllegalArgumentException();

			String line;
			file = new RandomAccessFile(args[1], "r");
			System.out.println("Bearbeite Datei \"" + args[1] + "\".\n");

			while ((line = file.readLine()) != null)// read all lines from the file as intervals separated by ','
			{
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				if (Algorithm == OptType.Interval)
					schedule.add(new Interval(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
				else if (Algorithm == OptType.Lateness)
					schedule.add(new Job(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
				else
					throw new RuntimeException();
			}

			printList("Es wurden " + schedule.size() + " Zeilen mit folgendem Inhalt gelesen:", schedule);

		} catch (NumberFormatException n)
		{
			System.err.println("Badly formated file.");
			System.exit(1);
		} catch (IllegalArgumentException e)
		{
			System.err.println("Usage:\nAnwendung Algorithm PathToFile");
			System.exit(1);
		} catch (IOException e2)
		{
			System.err.println("File not found.");
			System.exit(1);
		} finally
		{
			if (file != null)
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
	}

	/*
	 * Prints the message and all elements of the given list
	 */
	private static void printList(String message, ArrayList<TimeTuple> list)
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

	private static void printIntervalList(String message, ArrayList<Interval> list)
	{
		ArrayList<TimeTuple> tupel = new ArrayList<>();
		for (int i = 0; i < list.size(); i++)
		{
			tupel.add(list.get(i));
		}
		printList(message, tupel);
	}

	private static void printJobList(String message, ArrayList<Job> list)
	{
		ArrayList<TimeTuple> tupel = new ArrayList<>();
		for (int i = 0; i < list.size(); i++)
		{
			tupel.add(list.get(i));
		}
		printList(message, tupel);
	}

	/*
	 * Removes as few intervals as possible so that no two intervals overlap. Prefers intervals that end soon.
	 */
	public static ArrayList<Interval> intervalScheduling(ArrayList<Interval> intervals)
	{
		ArrayList<Interval> returnValue = new ArrayList<>();

		Collections.sort(intervals);// sorts by end values into ascending order
		printIntervalList("Sortiert:", intervals);

		int end = Integer.MIN_VALUE;// there is no previous task so the end of 'the last task' is the smallest number possible

		for (int i = 0; i < intervals.size(); i++)
		{
			if (end <= intervals.get(i).start)// if the last tasks starts before this task starts, skip this task
			{
				returnValue.add(intervals.get(i));
				end = intervals.get(i).end;

				for (int j = 1; j < returnValue.size(); j++)
					assert returnValue.get(j - 1).end <= returnValue.get(j).start;// check for overlap
			}
		}

		printIntervalList("Berechnetes Intervallscheduling:", returnValue);
		return returnValue;
	}

	/*
	 * Rearranges jobs so that the maxLateness is the smallest. Returns an array representing the progress through all jobs
	 */
	public static int[] latenessScheduling(ArrayList<Job> jobs)
	{
		int[] returnValue = new int[jobs.size()];

		Collections.sort(jobs);// sorts by deadlines into ascending order
		printJobList("Sortiert: ", jobs);

		int time = 0;
		int maxLateness = 0;

		for (int i = 0; i < returnValue.length; i++)//calculate times
		{
			returnValue[i] = time;

			time += jobs.get(i).duration;
			maxLateness = Math.max(maxLateness, time - jobs.get(i).deadline);
		}

		System.out.print("Berechnetes Intervallscheduling:\n[");
		for (int i = 0; i < returnValue.length; i++)//print times
		{
			if (i > 0)
				System.out.print(", ");

			System.out.print(returnValue[i]);
		}

		System.out.println(", " + time + "]\n\nBerechnete maximale Verspätung: " + maxLateness);

		return returnValue;
	}
}
