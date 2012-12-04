package GraviTux;

class Timer extends Play
{
	private final int timeTotal;
	private int currentTime;
	private boolean timeElapsed;

	public Timer()
	{
		currentTime = 0;
		timeTotal = 300;
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
