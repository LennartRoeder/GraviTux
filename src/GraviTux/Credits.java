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
	private Image credits, logofh, back, bg, GraviTux, creditnames;

	public Credits()
	{
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		bg = new Image("GraviTux/menu/BG_v4.png");
		credits = new Image("GraviTux/menu/credits_of.png");
		back = new Image("GraviTux/menu/back.png");
		GraviTux = new Image("GraviTux/menu/GraviTux.png");
		creditnames = new Image("GraviTux/menu/credit_name.png");
		logofh = new Image("GraviTux/menu/logo_fh.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		bg.draw(0, 0);
		creditnames.draw(150, 180);
		GraviTux.draw(130, 50);
		credits.draw(205, 15);
		back.draw(310, 530);
		logofh.draw(640, 450);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		//back button
		if ((posX > 310 && posX < 490) && (posY > (600 - 592) && posY < (600 - 530)))
		{
			if (Mouse.isButtonDown(0))
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
	}

	@Override
	public int getID()
	{
		return 2;
	}
}