package GraviTux;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class Highscores extends BasicGameState
{
	private Image highscoresof, bg, back, GraviTux;

	public Highscores()
	{
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		bg = new Image("GraviTux/menu/BG_v4_temp.png");
		back = new Image("GraviTux/menu/back.png");
		highscoresof = new Image("GraviTux/menu/highscores_of.png");
		GraviTux = new Image("GraviTux/menu/GraviTux.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		bg.draw(0, 0);
		back.draw(310, 530);
		highscoresof.draw(205, 15);
		GraviTux.draw(130, 50);
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
		return 3;
	}
}
