
public class Job implements Comparable<Job>, TimeTuple
{
	int duration;
	int deadline;
	public int getDuration(){return duration;}
	public int getDeadline(){return deadline;}
	public String toString(){return "["+duration+", "+deadline+"]";}

	public Job(int duration, int deadline){
		this.duration=duration;
		this.deadline=deadline;
	}
	
	public int compareTo(Job other){
		if(other.deadline==this.deadline)return 0;
		if(other.deadline>this.deadline)return -1;
		return 1;
	}
	@Override
	public int getFirstTime()
	{
		return getDuration();
	}
	@Override
	public int getSecondTime()
	{
		return getDeadline();
	}
}
