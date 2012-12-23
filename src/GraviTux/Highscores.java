package GraviTux;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

class Highscores extends BasicGameState
{
	private Image bg;

	public Highscores()
	{
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		bg = new Image("GraviTux/menu/BG_v4_temp.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		//Color for the top 20px background.
		g.setColor(new Color(126, 178, 222));
		g.fillRect(0, 0, 800, 21);
		g.setColor(Color.white);

		bg.draw(0, 20);

		Menu.menu.drawString(205, 35, "highscores of");
		Menu.header.drawString(130, 70, "GraviTux");
		Menu.menu.drawString(310, 550, "back");

		//output highscore
		for (int i = 0; i < Play.getLevelMax(); i += 2)
		{
			Menu.body.drawString(130, (220 + 20 * i), "Level " + (i + 1) + ": " + Play.getHighscore(i) + " Sec.");  //game timer
			Menu.body.drawString(420, (220 + 20 * i), "Level " + (i + 2) + ": " + Play.getHighscore(i) + " Sec.");  //game timer
		}
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
		return 3;
	}
}
