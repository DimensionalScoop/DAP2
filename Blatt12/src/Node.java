import java.util.ArrayList;

public class Node implements Comparable<Node>
{
	private ArrayList<Edge> adjacent;
	private int id;

	public ArrayList<Edge> Adjacent()
	{
		return adjacent;
	}

	public int Id()
	{
		return id;
	}

	public Node(int id)
	{
		this.id = id;
		adjacent = new ArrayList<>();
	}

	public void addEdge(Node dst)
	{
		if (!contains(new Edge(this, dst)))
			adjacent.add(new Edge(this, dst));
	}

	public boolean contains(Edge item)
	{
		for (Edge edge : adjacent)
		{
			if (edge.equals(item))
				return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Node))
			return false;
		if (((Node) obj).id == this.id)
			return true;
		return false;
	}
	
	@Override
	public String toString()
	{
		String value="Node #"+Id()+": ";
		
		for(int i=0;i<adjacent.size();i++)
		{
			value+=adjacent.get(i).Dst().Id();
			if(adjacent.size()-1!=i)
				value+=", ";
		}
		
		return value;
	}
	
	public int compareTo(Node item) {
	    return id-item.id;
	}
}
