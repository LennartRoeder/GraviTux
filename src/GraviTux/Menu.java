package GraviTux;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState
{
	Image playNow;
	Image exitGame;

	public Menu(int state)
	{
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		playNow = new Image("res/GraviTux/playNow.png");
		exitGame = new Image("res/GraviTux/exitGame.png");
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.drawString("Willkommen bei GraviTux", 294, 150);
		playNow.draw(294, 200);
		exitGame.draw(294, 275);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		//play now button
		if ((posX > 294 && posX < 505) && (posY > 349 && posY < 400))
		{
			if (Mouse.isButtonDown(0))
			{
				sbg.enterState(1);
			}
		}
		//exit game
		if ((posX > 294 && posX < 505) && (posY > 274 && posY < 325))
		{
			if (Mouse.isButtonDown(0))
			{
				System.exit(0);
			}
		}
	}

	public int getID()
	{
		return 0;
	}
}