package GraviTux;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

class Credits extends BasicGameState
{
	private Image logofh, bg;

	public Credits()
	{
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		bg = new Image("GraviTux/menu/BG_v4.png");
		logofh = new Image("GraviTux/menu/logo_fh.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		//Color for the top 20px background.
		g.setColor(new Color(126, 178, 222));
		g.fillRect(0, 0, 800, 21);
		g.setColor(Color.white);

		bg.draw(0, 20);

		Menu.menu.drawString(205, 35, "credits of");
		Menu.header.drawString(130, 70, "GraviTux");
		Menu.menu.drawString(150, 220, "Lennart Karsten,\nJoana Löpthien,\nStefan Grimm,\nChristian Kraft");
		Menu.menu.drawString(310, 550, "back");

		logofh.draw(640, 470);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		int posX = Mouse.getX();
		int posY = Mouse.getY();

		//back button
		if (Mouse.isButtonDown(0) && (posX > 310 && posX < 490) && (posY > 28 && posY < 90))
		{
			sbg.enterState(0);
			try
			{
				Thread.sleep(250);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getID()
	{
		return 2;
	}
}