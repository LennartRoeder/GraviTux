package GraviTux;

class Timer extends Play
{
	private final int timeTotal;
	private int currentTime;
	private boolean timeElapsed;

	public Timer(int time)
	{
		currentTime = 0;
		timeTotal = time;
		timeElapsed = false;
	}

	public void addTime(int time)
	{
		currentTime += time;
		if (currentTime >= timeTotal)
		{
			timeElapsed = true;
		}
	}

	public String getTime()
	{
		return String.format("%.1f", currentTime / 1000f);
	}

	public boolean isTimeElapsed()
	{
		return timeElapsed;
	}

	public void reset()
	{
		currentTime = 0;
		timeElapsed = false;
	}
}
