public class Edge
{
	private Node src;
	private Node dst;

	public Node Src()
	{
		return src;
	}

	public Node Dst()
	{
		return dst;
	}

	public Edge(Node src, Node dst)
	{
		this.src = src;
		this.dst = dst;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Edge))
			return false;
		if (((Edge) obj).src == this.src && ((Edge) obj).dst == this.dst)
			return true;
		return false;
	}
}
