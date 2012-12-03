package GraviTux;

class Timer extends Play
{
	private final int timeTotal;
	private int currentTime;
	private boolean timeElapsed;

	public Timer()
	{
		timeTotal = 300;
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
