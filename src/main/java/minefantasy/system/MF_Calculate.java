package minefantasy.system;

public class MF_Calculate {
	public static long getTicksForDays(int days)
	{
		return days * 24 * 60 * 60 * 20;
	}
	
	public static long getTicksForHours(int hours)
	{
		return hours * 60 * 60 * 20;
	}
	
	public static long getTicksForMinutes(int mins)
	{
		return mins * 60 * 20;
	}
	
	public static long getTicksForTime(int days, int hours, int mins)
	{
		return getTicksForDays(days) + getTicksForHours(hours) + getTicksForMinutes(mins);
	}
}
