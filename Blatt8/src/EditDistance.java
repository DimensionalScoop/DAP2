import java.awt.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class EditDistance
{
	/*
	 * calcs all Levenshtein distances
	 */
	static int[][] distanceMatrix(char[] a, char[] b){
		//dis[i][j] is used to save the distance of the first i letters of a to the first j letters of b
		int[][] dis= new int[a.length+1][b.length+1];//also initializes all items as 0
		
		//to transform the first i letters to "" we need to delete all letters
		for(int i=0;i<=a.length;i++)
			dis[i][0]=i;
		
		//vice versa: from "" to a string of i letters, i letters need to be inserted
		for(int i=0;i<=b.length;i++)
			dis[0][i]=i;
		
		for(int i=1;i<=a.length;i++)
			for(int j=1;j<=b.length;j++)
			{
				if(a[i-1]==b[j-1])
					dis[i][j]=dis[i-1][j-1];//doesn't require a change as both letters are already the same
				else
				{
					dis[i][j]=min(//choose the cheapest operation to make the i-th letter equal to the j-th
							dis[i-1][j]+1,//delete
							dis[i][j-1]+1,//insert
							dis[i-1][j-1]+1//replace
							);
				}
			}
		
		return dis;
	}
	
	/*
	 * calcs Levenshtein distance
	 */
	static int distance(String a, String b){
		
		return distanceMatrix(a.toCharArray(),b.toCharArray())[a.length()][b.length()];
	}
	
	/*
	 * prints a step-by-step Levenshtein distance analysis
	 */
	private static void printEditOperations(String a, String b)
	{
		printEditOperations(a.toCharArray(), b.toCharArray());
	}
	
	/*
	 * prints a step-by-step Levenshtein distance analysis
	 */
	private static void printEditOperations(char[] a, char[] b)
	{
		System.out.print("\n--------------- \t\t\t----------------");
		
		int[][] dis=distanceMatrix(a, b);
		
		int i=a.length;
		int j=b.length;
		int stepCounter=1;
		ArrayList<Character> sequence=new ArrayList<>();
		
		for(int n=0;n<a.length;n++)//copy 'a' into sequence
			sequence.add(a[n]);

//		for(int y=0;y<=j;y++){
//			System.out.println();
//			for (int x = 0; x <= i; x++)
//			{
//				System.out.print(dis[x][y] + " ");
//			}
//		}

		while (i != 0 && j != 0)
		{
			int nextStep = min(dis[i - 1][j],// delete
					dis[i][j - 1],// insert
					dis[i - 1][j - 1]// replace
			);
//			System.out.print("\nNext Step: " + nextStep+" ("+i+", "+j+")");

			
			if (nextStep == dis[i - 1][j - 1])
			{
				if (dis[i][j] == dis[i - 1][j - 1])// no change
				{
					System.out.print("\n" + stepCounter++ + ") No edit \tNo change \t\t");
					printSequence(sequence);

				} else
				// a replacement
				{
					sequence.set(i -1, b[j - 1]);
					System.out.print("\n" + stepCounter++ + ") One edit \tReplacing " + a[i - 1] + " with " + b[j - 1] + " \t");
					printSequence(sequence);
				}
				
				i--;
				j--;
				
			} else if (nextStep == dis[i - 1][j])
			{
				sequence.remove(i );
				System.out.print("\n" + stepCounter++ + ") One edit \tDeleting " + a[i - 1] + " \t\t");
				printSequence(sequence);
				i--;
				
			} else if (nextStep == dis[i][j - 1])
			{
				sequence.add(i , b[j - 1]);
				System.out.print("\n" + stepCounter++ + ") One edit \tInserting " + b[j - 1] + " \t\t");
				printSequence(sequence);
				
				j--;
			} else
			{
				throw new RuntimeException();
			}
		}

		System.out.println("\n--------------- \t\t\t----------------");
	}
	
	static void printSequence(ArrayList<Character> sequence)
	{
		for (Character c : sequence)
			System.out.print(c);
	}
	
	static int min(int a, int b, int c){
		return Math.min(a, Math.min(b, c));
	}
	
	static String[] readAllLines(String file) throws IOException{
		return Files.readAllLines(Paths.get(file),Charset.defaultCharset()).toArray(new String[0]);
	}
	
	static boolean verbose=false;
	static String sequence1="", sequence2="";
	static String[] sequences=new String[0];
	
	/*
	 * parses command line params and exits program on encountering any parsing errors
	 */
	static void parseParams(String[] args)
	{
		try
		{
			switch (args.length)
			{
			case 1://a file
				sequences = readAllLines(args[0]);
				break;

			case 2://either a file and verbose or two sequences
				if (args[1].equalsIgnoreCase("-o") || args[1].equalsIgnoreCase("-v"))
				{
					verbose = true;
					sequences = readAllLines(args[0]);
				} else
				{
					sequence1 = args[0];
					sequence2 = args[1];
				}
				break;

			case 3://two sequences and verbose
				sequence1 = args[0];
				sequence2 = args[1];
				if (args[2].equalsIgnoreCase("-o") || args[2].equalsIgnoreCase("-v"))
					verbose = true;
				else
					throw new IllegalArgumentException();
				break;

			default:
				throw new IllegalArgumentException();
			}

		}
		catch (IOException e2)
		{
			System.err.println("Error while parsing file.");
			System.exit(1);
		}
		catch (IllegalArgumentException e)
		{
			System.err.println("Invaild parameters.\n\nUsage:\nEditDistance file [-v]\nEditDistance sequence1 sequence2 [-v]\n\n-v or -o: Verbose output");
			System.exit(1);
		}
	}
	
	public static void main(String[] args){
		parseParams(args);
		
		if(sequence1.isEmpty())//use strings read from file
		{
			for(int i=0;i< sequences.length;i++)
				for(int j=i+1;j<sequences.length;j++)
				{
					System.out.print("\n\nDifference between \""+sequences[i]+"\" and \""+sequences[j]+"\": "+distance(sequences[i], sequences[j])+" edits.");
					if(verbose)printEditOperations(sequences[i], sequences[j]);
				}
		}
		else//use strings from command line
		{
			System.out.println("Difference between \""+sequence1+"\" and \""+sequence2+"\": "+distance(sequence1, sequence2)+" edits.");
			if(verbose)printEditOperations(sequence1, sequence2);
		}
	}
}
