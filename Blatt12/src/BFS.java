import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class BFS
{
	static String file;
	static int startNodeID;
	
	
	public static void main(String[] args)
	{
		boolean testing = false;
		Graph graph=null;
		
		parseParams(args);
		
		try
		{
			graph= Graph.fromFile(file);
			
		} catch (IOException e)
		{
			System.err.println("File not found.");
			System.exit(1);
		}
		

		if (!testing)
		{
			ArrayList<Integer> distance=bfs(graph,startNodeID);
			System.out.println("Distances from Node "+startNodeID+": ");
			for(int i=0;i<distance.size();i++)
			{
				System.out.println("Node #"+graph.Nodes().get(i).Id()+": "+distance.get(i));
			}
			
		} else
		{
			System.out.print(graph);
			graph.addNode(6);
			graph.addEdge(5, 6);
			graph.addEdge(5, 6);
			graph.addEdge(6, 5);
			System.out.print(graph);
		}
	}

	public static ArrayList<Integer> bfs(Graph graph, int start)
	{
		if (!graph.contains(start))
			throw new IllegalArgumentException("Graph doesn't contain start.");
		
		graph.makeBidirectional();
		graph.sort();//make sure that nodes[i].id=i

		LinkedList<Integer> q = new LinkedList<>();
		ArrayList<Integer> d = new ArrayList<>();
		ArrayList<Integer> color = new ArrayList<>();
		ArrayList<Integer> previous = new ArrayList<>();

		for (int i = 0; i < graph.Nodes().size(); i++)
		{
			d.add(null);
			color.add(0);// set color to black
			previous.add(null);
		}

		d.set(start, 0);
		color.set(start, 1);// set color to gray: node was discovered
		q.offer(start);

		while (q.size() > 0)
		{
			int u = q.remove();

			for (Edge adj : graph.Nodes().get(u).Adjacent())
			{
				int dst = adj.Dst().Id();
				if (color.get(dst) == 0)
				{
					color.set(dst, 1);// set color to gray: node was discovered
					d.set(dst, (int)d.get(u) + 1);
					previous.set(dst, u);
					q.offer(dst);
				}
			}
			color.set(u, 2);// set color to black after finishing node
		}
		return d;
	}
	
	/*
	 * parses command line params and exits program on encountering any parsing errors
	 */
	static void parseParams(String[] args)
	{
		try
		{
			switch (args.length)
			{
			case 2:
				file=args[0];
				startNodeID=Integer.parseInt(args[1]);
				
				if(!file.toLowerCase().endsWith(".graph"))
					throw new IllegalArgumentException();
				
				if(startNodeID<0)
					throw new IllegalArgumentException();
				break;
				
			default:
				throw new IllegalArgumentException();
			}

		}
		catch (IllegalArgumentException e)
		{
			System.err.println("Invaild parameters.\n\nUsage:\nBFS file.graph startNode");
			System.exit(1);
		}
	}
}
