package GraviTux;

public class Timer
{
	private int timeTotal;
	private int currentTime;
	private boolean timeElapsed;

	public Timer(int timeInMillis)
	{
		timeTotal = timeInMillis;
		timeElapsed = false;
		currentTime = 0;
	}

	public void addTime(int time)
	{
		currentTime += time;
		if (currentTime >= timeTotal)
		{
			timeElapsed = true;
		}
	}

	public boolean isTimeElapsed()
	{
		return timeElapsed;
	}

	public void reset()
	{
		timeElapsed = false;
		currentTime = 0;
	}
}
