
public class Interval implements Comparable<Interval>
{
	int start;
	int end;
	public int getStart(){return start;}
	public int getEnd(){return end;}
	public String toString(){return "["+start+", "+end+"]";}

	public Interval(int start, int end){
		this.start=start;
		this.end=end;
	}
	
	public int compareTo(Interval other){
		if(other.end==this.end)return 0;
		if(other.end>this.end)return -1;
		return 1;
	}
}
