
public abstract class Simplex
{
	Point[] corners;
	int dim;
	
	public Simplex(Point...points){
		if (points.length == 0)
			throw new IllegalArgumentException();
		
		this.corners=points;
		dim=points.length-1;
	}
	
	public Point get(int i)
	{
		if (i < 0 || i > dim)
			throw new IllegalArgumentException();
		return corners[i];
	}
	
	/*
	 * Determines the sum of the distances between all neighboring points
	 */
	public double perimeter(){
		double returnValue=0;
		
		if(dim==0)return 0;
		
		for(int i=0;i<corners.length-1;i++)
			returnValue+=get(i).distance(get(i+1));
		
		if(dim>1)//all shapes with at least three corners have an edge connecting the first with the last corner
			returnValue+=get(dim).distance(get(0));
		
		return returnValue;
	}
	
	public abstract boolean validate();
}
