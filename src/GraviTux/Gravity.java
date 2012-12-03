package GraviTux;

public class Gravity extends Play
{
	private static final float gravitySpeedMax = 7f;  //tux maximum falling speed

	public Gravity()
	{
		//gravity = 'b';
		setGravity('b');
	}

	public void setGravity(char newGravity)
	{
		if (newGravity == 'b' || newGravity == 't' || newGravity == 'l' || newGravity == 'r')
		{
			gravity = newGravity;
		}
		else
		{
			gravity = newGravity;
		}

	}

	public char getGravity()
	{
		return gravity;
	}

	public void flipGravity()
	{
		//System.out.println(tux.getImage(0));
		switch (gravity)
		{
			case 'b':
				gravity = 't';
				tux = topStanding;
				break;
			case 't':
				gravity = 'b';
				tux = bottomStanding;
				break;
			case 'l':
				gravity = 'r';
				tux = rightStanding;
				break;
			case 'r':
				gravity = 'l';
				tux = leftStanding;
				break;
		}
		//System.out.println(tux.getImage(0));
		//timer.reset();
	}

	public void rotateGravity()
	{
		switch (gravity)
		{
			case 'b':
				gravity = 'l';
				tux = leftStanding;
				tuxWidth = tux.getHeight(); //rotates the image when gravity is rotated
				tuxHeight = tux.getWidth();
				break;
			case 't':
				gravity = 'r';
				tux = rightStanding;
				tuxWidth = tux.getHeight();
				tuxHeight = tux.getWidth();
				break;
			case 'l':
				gravity = 't';
				tux = topStanding;
				tuxWidth = tux.getHeight();
				tuxHeight = tux.getWidth();
				break;
			case 'r':
				gravity = 'b';
				tux = topStanding;
				tuxWidth = tux.getHeight();
				tuxHeight = tux.getWidth();
				break;
		}
		//timer.reset();
	}

	public void fall(float gravitySpeed, int delta)
	{
		////accelerate falling
		gravitySpeed += gravityAcc * delta;

		////limit falling speed
		if (gravitySpeed > gravitySpeedMax)
		{
			gravitySpeed = gravitySpeedMax;
		}

		//tux.update(delta);

		switch (gravity)
		{
			case 'b':
				tuxY += gravitySpeed;
				break;
			case 't':
				tuxY -= gravitySpeed;
				break;
			case 'l':
				tuxX -= gravitySpeed;
				break;
			case 'r':
				tuxX += gravitySpeed;
				break;
		}
	}
}

