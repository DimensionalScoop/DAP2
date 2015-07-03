import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;

public class Graph
{
	private ArrayList<Node> nodes = new ArrayList<>();
	public ArrayList<Node> Nodes(){return nodes;}

	public Graph()
	{
	}

	/*
	 * Creates a graph from a parsed file
	 */
	public static Graph fromFile(String filepath) throws IOException
	{
		Graph graph=new Graph();
		String line;
		RandomAccessFile file = new RandomAccessFile(filepath, "r");
		System.out.println("Bearbeite Datei \"" + filepath + "\".\n");

		try
		{
			while ((line = file.readLine()) != null)// read all lines from the file as intervals separated by ','
			{
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				int src=Integer.parseInt(tokenizer.nextToken());
				int dst=Integer.parseInt(tokenizer.nextToken());
				
				if(!graph.contains(src))
					graph.addNode(src);
				if(!graph.contains(dst))
					graph.addNode(dst);
				graph.addEdge(src, dst);
			}
		} catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("Malformed graph file.");
		} finally
		{
			file.close();
		}
		
		return graph;
	}

	/*
	 * Checks whether graph contains a node with the given ID
	 */
	public boolean contains(int id)
	{
		for (Node node : nodes)
		{
			if (node.Id() == id)
				return true;
		}
		return false;
	}

	public void addNode(int id)
	{
		if (contains(id))
			throw new IllegalArgumentException("Id already exist.");
		nodes.add(new Node(id));
	}

	public Node getNode(int id)
	{
		for (Node node : nodes)
		{
			if (node.Id() == id)
				return node;
		}
		return null;
	}

	public void addEdge(int src, int dst)
	{
		if (!contains(src))
			throw new IllegalArgumentException("Src doesn't exist.");
		if (!contains(dst))
			throw new IllegalArgumentException("Dst doesn't exist.");

		getNode(src).addEdge(getNode(dst));
	}
	
	@Override
	public String toString()
	{
		String value="Graph with following nodes: \n";
		
		for (Node node : nodes)
		{
			value+=node+"\n";
		}
		
		return value;
	}

	/*
	 * makes sure that if there is an edge from a to b that there is also an edge from b to a
	 */
	public void makeBidirectional()
	{
		for(int x=0;x<nodes.size();x++)
			for(int y=0;y<nodes.size();y++)
				if(x!=y)
					if(nodes.get(x).contains(new Edge(nodes.get(x), nodes.get(y))))
						if(!nodes.get(y).contains(new Edge(nodes.get(y), nodes.get(x))))
							nodes.get(y).addEdge(nodes.get(x));
	}

	/*
	 * Sorts nodes by id
	 */
	public void sort()
	{
		Collections.sort(nodes);
	}
}
