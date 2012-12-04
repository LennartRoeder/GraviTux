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
	private Image welcomeMenu, newGame, resume, highscore, credits, exit, bg;

	public Menu()
	{
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		bg = new Image("GraviTux/menu/BG_v4.png");
		welcomeMenu = new Image("GraviTux/menu/welcome_menu.png");
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
		welcomeMenu.draw(262, 60);
		newGame.draw(310, 190);
		resume.draw(310, 250);
		highscore.draw(310, 310);
		credits.draw(310, 370);
		exit.draw(310, 430);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		//play now button
		if ((posX > 309 && posX < 491) && (posY > (600 - 229) && posY < (600 - 189)))
		{
			if (Mouse.isButtonDown(0))
			{
				sbg.enterState(1);
			}
		}
		//exit game
		if ((posX > 309 && posX < 491) && (posY > (600 - 449) && posY < (600 - 429)))
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