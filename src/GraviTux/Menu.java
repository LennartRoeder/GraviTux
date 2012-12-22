package GraviTux;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

class Menu extends BasicGameState
{
	private Image welcome, GraviTux, newGame, resume, highscore, credits, exit, bg;

	public Menu()
	{
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		bg = new Image("GraviTux/menu/BG_v4.png");
		welcome = new Image("GraviTux/menu/welcome.png");
		GraviTux = new Image("GraviTux/menu/GraviTux.png");
		newGame = new Image("GraviTux/menu/new_game.png");
		resume = new Image("GraviTux/menu/continue.png");
		highscore = new Image("GraviTux/menu/highscore.png");
		credits = new Image("GraviTux/menu/credits.png");
		exit = new Image("GraviTux/menu/exit.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		bg.draw(0, 0);
		welcome.draw(205, 15);
		GraviTux.draw(130, 50);
		newGame.draw(210, 190);
		resume.draw(210, 250);
		highscore.draw(210, 310);
		credits.draw(210, 370);
		exit.draw(210, 430);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		//new game button
		if ((posX > 210 && posX < 490) && (posY > (600 - 252) && posY < (600 - 190)))
		{
			if (Mouse.isButtonDown(0))
			{
				Play.newLevel();
				sbg.enterState(1);
			}
		}
		//continue
		if ((posX > 210 && posX < 490) && (posY > (600 - 312) && posY < (600 - 250)))
		{
			if (Mouse.isButtonDown(0))
			{
				sbg.enterState(1);
			}
		}
		//credits
		if ((posX > 210 && posX < 490) && (posY > (600 - 432) && posY < (600 - 370)))
		{
			if (Mouse.isButtonDown(0))
			{
				sbg.enterState(2);
			}
		}
		//highscores
		if ((posX > 210 && posX < 490) && (posY > (600 - 372) && posY < (600 - 310)))
		{
			if (Mouse.isButtonDown(0))
			{
				sbg.enterState(3);
			}
		}
		//exit game
		if ((posX > 210 && posX < 490) && (posY > (600 - 492) && posY < (600 - 430)))
		{
			if (Mouse.isButtonDown(0))
			{
				System.exit(0);
			}
		}
	}

	@Override
	public int getID()
	{
		return 0;
	}
}