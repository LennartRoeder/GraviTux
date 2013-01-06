package GraviTux;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

class Credits extends BasicGameState
{
	private Image bg;

	public Credits()
	{
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		bg = new Image("GraviTux/menu/BG_v6.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		bg.draw(0, 0);

		Menu.menu.drawString(270, 50, "credits of");
		Menu.header.drawString(210, 70, "GraviTux");
		Menu.menu.drawString(270, 220, "Lennart Karsten,\nJoana Löpthien,\nStefan Grimm,\nChristian Kraft");
		Menu.menu.drawString(240, 450, "FH Flensburg 2013");
		Menu.menu.drawString(350, 550, "back");

		//FOR TEXT PLACEMENT ONLY!!!
/*		g.drawString("Textmasse als Hilfe zum Platzieren von Text", 10, 10);

		g.drawString("back breite: " + Menu.menu.getWidth("back"), 10, 30);
		g.drawString("back hoehe: " + Menu.menu.getHeight("back"), 250, 30);
		g.drawRect(310, 550+7, Menu.menu.getWidth("back"), Menu.menu.getHeight("back"));*/
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		int posX = Mouse.getX();
		int posY = Mouse.getY();

		//back button
		if (Mouse.isButtonDown(0) && (posX > 359 && posX < 473) && (posY > 11 && posY < 64))
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